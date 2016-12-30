#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#define CCOUNT 3

/* Send our pid on "out" for the next process in the triangle to receive. Get
 * a pid from the prior process in the triangle  via "in", add it to the total
 * and pass the pid along, repeating enough times so that all processes see 
 * each pid once. When done, send the total to the parent via "report". */
void AddPidsAndSendTotal(int report, int in, int out, int pid) {
   int i, total = pid;

   


   
   write(report, &total, sizeof(total));

   close(out);
   /* Check that there is no lingering data on "in" */
   if (0 != read(in, &pid, sizeof(pid)))
      printf("Missing EOF on pipe.");

   /* Relevant cleanup */
   


}

int main() {
   int i, checkTotal, pidTotal;

   // Pipes to parent, and between three children A, B, C
   int reportFds[2], pAtoB[2], pBtoC[2], pCtoA[2]; 
   
   pipe(reportFds);
   pipe(pCtoA);
   pipe(pAtoB);

   if (!fork()) { /* Child A */
      


      AddPidsAndSendTotal(reportFds[1], pCtoA[0], pAtoB[1], getpid());
   }
   else {
      /* Set up for child B */
      /*1*/
      



      if (!fork()) { // Do child B
         


         AddPidsAndSendTotal(reportFds[1], pAtoB[0], pBtoC[1], getpid());
      }
      else {
         /* Set up for child C */
         



         if (!fork()) { // Do child C
            close(reportFds[0]);
            AddPidsAndSendTotal(reportFds[1], pBtoC[0], pCtoA[1], getpid());
         }
         else {
            


            close(pBtoC[0]);
            close(pCtoA[1]);

            for (i = pidTotal = 0; i < CCOUNT; i++) {
               pidTotal += wait(NULL);
               printf("Child exits..\n");
            }
            for (i = 0; i < CCOUNT; i++)
               if (0 < read(reportFds[0], &checkTotal, sizeof(checkTotal))
                && checkTotal == pidTotal)
                  printf("Good report\n");

            if (0 == read(reportFds[0], &checkTotal, sizeof(checkTotal)))
               printf("Good EOF\n");

            close(reportFds[0]);
         }
      }
   }

   return 0;
}
