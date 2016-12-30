#include <stdio.h>
#include <unistd.h>

#define MIN 3

int main(int argc, char **argv) {
   char **ptr;
   
   for (ptr = argv + 1; *ptr; ptr++)
      printf("%s ", *ptr);
   printf("|%s|\n", argv[0]);
   if (argc > MIN) {        
      argv[1] = argv[0];
      argv[argc - 1] = 0;        
      execvp(argv[0], argv+1);
   }
   printf("Done!");
      
   return 0;   
}

