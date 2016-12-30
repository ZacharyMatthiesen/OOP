/*environment passed down from parent to child 

we need to take on 

FILE DESCRIPTOR 

0 = stdin = keyboard
1 = stdout = monitor
2 = stderror = monitor //dont redirect this

stdin 
stdout
stderror 


  //you can redirect File descriptor 1 the pipe 
  //you can redirect File descriptor 0 to the pipe


example 


*/

int fds[2], childPID1, childPID2;

pipe(fds);

if((childPID1 = fork()) == 0){
	dup2(fds[1], 1);
	close(fds[0]);
	close(fds[1]);
	execlp(“ls”, ”ls”, NULL);
}

//we are gone after exec beacuse of the brain wipe, unless there is an error

if((childPID2 == fork()) == 0) {
	dup2(fds[0], 0);
	close(fds[0]);
	close(fds[1]);
	execlp(“less”, “less”, NULL);
}

close(fds[0]);
close(fds[1]);
waitpid(childPID1, NULL, 0);
printf(“child 1 is done \n”);
waitpid(childPID2, NULL, 0);
printf(“child 2 is done \n”);
printf(“>”);

//when you read from a file eventually the file runs out of data EOF char is important here
//----> if you have a pipe, and you do a system call to read from the file, there is a way
//----> cat expects its input file to have EOF, if it does not have get that char
// what would be necessary to indicate end of pipe?
// Everybody must be done with the pipe before the pipe gets EOF
// If the parent is waiting, then the pipe is still waiting, no EOF
// NO DATA && NO WRITERS --> the pipe gets closed, EOF
// NAIL EVERY EXTRA FILE DESCRIPTOR OPEN TO AVOID HANING

//remember the sequence is arbitrary



/*
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
*/


void TwosComplement(char num[]) {
   int stringToInt = 0, endIndex = 0, place = 0;
   const char *Zero = DIGIT_ZERO, *One = DIGIT_ONE;
   while (num[endIndex++])
      ;
   
   while (endIndex-- > 0) {
      if (num[endIndex] == DIGIT_ZERO) 
         ;
      else {
         stringToInt += 2^(place++);
      }   
   }
   
   stringToInt = ~stringToInt;
   stringToInt++;
   endIndex = 0;
   
   while (endIndex-- > 0) {
      if ((stringToInt & ((stringToInt<<1)>>1)) == 0) {
         strcat(num, Zero);
      }
      else {
         strcat(num, One);
      }   
   }
   
  
} 
