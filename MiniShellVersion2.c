#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include "SmartAlloc.c"

/* Max length of commands, arguments, filenames */
#define MAX_WORD_LEN 100
#define WORD_FMT "%100s"

/*Command Flags*/
#define RIGHT_CHEVRON 0x50
#define APPEND_FILE 0x100
#define STANDARD_FILE 0x200
#define OVER_WRITE 0x300 
#define STANDARD_PIPE 0x400
#define SOURCE_SET 0x500
#define ENVIRONMENT_SET 0x600
#define DIRECTORY_CHANGE 0x700


/* One argument in a commandline (or the command itself) */
typedef struct Arg {
   char value[MAX_WORD_LEN+1];
   struct Arg *next;
} Arg;

/* One full command: executable, arguments and any file redirections. */
typedef struct Command {
   int numArgs;
   int flags;
   Arg *args;
   char *singleArg;
   char inFile[MAX_WORD_LEN+1];
   char outFile[MAX_WORD_LEN+1];
   struct Command *next;
} Command;

/* Make a new Arg, containing "str" */
static Arg *NewArg(char *str) {
   Arg *rtn = malloc(sizeof(Arg));

   strncpy(rtn->value, str, MAX_WORD_LEN);
   rtn->next = NULL;

   return rtn;
}

/* Make a new Command, with just the executable "cmd" */
static Command *NewCommand(char *cmd) {
   Command *rtn = malloc(sizeof(Command));

   rtn->numArgs = 1;
   rtn->args = NewArg(cmd);

   rtn->inFile[0] = rtn->outFile[0] = '\0';
   rtn->next = NULL;

   return rtn;
}
/* Delete "cmd" and all its Args. */
static Command *DeleteCommand(Command *cmd) {
   Arg *temp;
   Command *rtn = cmd->next;
   while (cmd->args != NULL) {
      temp = cmd->args;
      cmd->args = temp->next;
      free(temp);
   }
   free(cmd);
   return rtn;
}
/*change the current directory according to the 
behavior of cd as described in MiniShell spec docs*/
int ChangeDir(char *pathName) {
   return chdir(pathName);
}


/* Read from "in" a single commandline, comprising one more pipe-connected
   commands.  Return head pointer to the resultant list of Commmands */
static Command *ReadCommands(FILE *in) {
   int nextChar;
   char nextWord[MAX_WORD_LEN+1];
   Command *rtn, *lastCmd;
   Arg *lastArg;

   /* If there is an executable, create a Command for it, else return NULL. */
   if (1 == fscanf(in, WORD_FMT, nextWord)) {
      rtn = lastCmd = NewCommand(nextWord);
      lastArg = lastCmd->args;
   }
   else
      return NULL;

   /* Repeatedly process the next blank delimited string */
   do {
      while ((nextChar = getc(in)) == ' ')   /* Skip whitespace */
         ;

      /* If the line is not over */
      if (nextChar != '\n' && nextChar != EOF) {

         /* A pipe indicates a new command */
         if (nextChar == '|') {
            if (1 == fscanf(in, WORD_FMT, nextWord)) {
               lastCmd = lastCmd->next = NewCommand(nextWord);
               lastArg = lastCmd->args;
            }
         }
         else if (!strcmp(nextWord, "|&")) { //redirect both stderr and stdout to the specifed pipe
            if (1 == fscanf(in, WORD_FMT, nextWord)) {
               lastCmd = lastCmd->next = NewCommand(nextWord);
               lastArg = lastCmd->args;
            }
            lastCmd->flags = STANDARD_PIPE;
         }
         /* Otherwise, it's either a redirect, or a commandline arg */
         else {
            ungetc(nextChar, in);
            fscanf(in, WORD_FMT, nextWord);

	    fscanf(in, WORD_FMT, nextWord);
            if (!strcmp(nextWord, "<"))
               fscanf(in, WORD_FMT, lastCmd->inFile);
            else if (!strcmp(nextWord, ">")) { //If the file exists, print to stderr the message "Redirection would overwrite <filename>"
               fscanf(in, WORD_FMT, lastCmd->outFile);
               lastCmd->flags = RIGHT_CHEVRON;
            }
            else if (!strcmp(nextWord, ">>")) { //append to the specified file
               fscanf(in, WORD_FMT, lastCmd->outFile);
               lastCmd->flags = APPEND_FILE;
            }
            else if (!strcmp(nextWord, ">&")) { //redirect stderr and stdout to the specified file and send user message if they try to overwrite
               fscanf(in, WORD_FMT, lastCmd->outFile);
               lastCmd->flags = STANDARD_FILE;
            }
            else if (!strcmp(nextWord, ">!")) { //overwrites the existing file automatically, same behavior as original > comand
               fscanf(in, WORD_FMT, lastCmd->outFile);
               lastCmd->flags = OVER_WRITE;
            }
            else if (!strcmp(nextWord, "cd"))
               fscanf(in, WORD_FMT, lastCmd->singleArg);
            else if (!strcmp(nextWord, "source")) {
               fscanf(in, WORD_FMT, lastCmd->outFile);
            }
            else {
               lastArg = lastArg->next = NewArg(nextWord);
               lastCmd->numArgs++;
            }
         }
      }
   } while (nextChar != '\n' && nextChar != EOF);

   return rtn;
}

//static void Source()

static void RunCommands(Command *cmds) {
   Command *cmd;
   char **cmdArgs, **thisArg;
   Arg *arg;
   int childPID, cmdCount = 0;
   int pipeFDs[2]; /* Pipe fds for pipe between this command and the next */
   int outFD = -1; /* If not -1, FD of pipe or file to use for stdout */
   int inFD = -1;  /* If not -1, FD of pipe or file to use for stdin */

   for (cmd = cmds; cmd != NULL; cmd = cmd->next) {
      if (inFD < 0 && cmd->inFile[0]) /* If no in-pipe, but input redirect */
         inFD = open(cmd->inFile, O_RDONLY);

      if (cmd->next != NULL && cmd->flags != STANDARD_PIPE)  /* If there's a next command, make an out-pipe */
         pipe(pipeFDs);
      else if (cmd->next != NULL && cmd->flags == STANDARD_PIPE) {
         dup2(pipeFDs[1], 1);
	 dup2(pipeFDs[1], 2);
         pipe(pipeFDs);
      }

      if ((childPID = fork()) < 0)
         fprintf(stderr, "Error, cannot fork.\n");
      else if (childPID) {      /* We are parent */
         cmdCount++;
         close(inFD);           /* Parent doesn't use inFd; child does */
         if (cmd->next != NULL) {
            close(pipeFDs[1]);  /* Parent doesn't use out-pipe; child does */
            inFD = pipeFDs[0];  /* Next child's inFD will be out-pipe reader */
         }
      }
      else {                         /* We are child */
         if (inFD >= 0) {            /* If special input fd is set up ...  */
            dup2(inFD, 0);           /*   Move it to fd 0 */
            close(inFD);             /*   Close original fd in favor of fd 0 */
         }

         outFD = -1;                 /* Set up special stdout, if any */
         if (cmd->next != NULL) {    /* if our parent arranged an out-pipe.. */
            outFD = pipeFDs[1];      /*   Save its write fd as our outFD */
            close(pipeFDs[0]);       /*   Close read fd; next cmd will read */
         }
         if (outFD < 0 && cmd->outFile[0]) { /*if redirect, output to the specified file*/
            if (cmd->flags == RIGHT_CHEVRON)
               outFD = open(cmd->outFile, O_WRONLY|O_CREAT|O_TRUNC, 0644);
            else if (cmd->flags == APPEND_FILE)
               outFD = open(cmd->outFile, O_WRONLY|O_CREAT|O_APPEND, 0644);
            else if (cmd->flags == STANDARD_FILE)
               outFD = open(cmd->outFile, O_WRONLY|O_CREAT|O_TRUNC, 0644);
               dup2(outFD, 2);
               dup2(outFD, 1);
            else if (cmd->flags == OVER_WRITE)
               outFD = open(cmd->outFile, O_WRONLY|O_CREAT|O_TRUNC, 0644);
         }
         /* If above code results in special stdout, dup this to fd 1 */
         if (outFD >= 0) {
            dup2(outFD, 1);
            close(outFD);
         }

         /* Build a commandline arguments array, and point it to Args content */
         cmdArgs = thisArg = calloc(sizeof(char *), cmd->numArgs+1);
         for (arg = cmd->args; arg != NULL; arg = arg->next)
            *thisArg++ = arg->value;
         /* Exec the command, with given args, and with stdin and stdout
            remapped if we did so above */
         execvp(*cmdArgs, cmdArgs);
      }
   }
   /*wait untill all forgrounded processes are done don't wait any more*/
   /*save all ids comming How do I know the job is done*/
   /*down counter, and you can use the counter and burnout by putting in a zero, traverse the jobs list and you can see the jobs that are burned out
   so the solution is to use a count how many are not minus ones and than jobs are counted down in a wile loop pid to relevant job map rapidly look up
   use a struct a binary tree structure*/


   /* Wait on all children */
   while (cmdCount--)
      wait(NULL);
}

int main() {
   Command *cmds;

   /* Repeatedly print a prompt, read a commandline, run it, and delete it */
   while (!feof(stdin)) {
      printf(">> ");
      if (NULL != (cmds = ReadCommands(stdin))) {
         RunCommands(cmds);
      }
      while (cmds != NULL) {
         cmds = DeleteCommand(cmds);
      }
   }
}
  
