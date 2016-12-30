#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "SmartAlloc.h" 
#define IDIM1 10 
#define IDIM2 4 
// to this larger block, which the caller is expected to use instead of |blk|.


int *Insert(int *toInsert, int *blk, int dim1, int dim2, int offset) { // Base: 7, Surcharge: 0
                                                                     
   int *temp = calloc(dim1 + dim2, sizeof(int));                       
   temp = memcpy(temp, blk, sizeof(int) * dim1);                       
   free(blk);                                                          
   return memcpy(temp, toInsert, sizeof(int) * offset);                
                                                                     
                                                                       
   return toInsert;                                                    // Base: 1, Surcharge: 0
}   


void ScanInts(int *arr, int size) {
   int i;
   
   for (i = 0; i < size; i++)
      scanf("%d", arr + i);
}

void PrintInts(char *name, int *arr, int size) {
   int i;
   
   printf("%s:", name);
   for (i = 0; i < size; i++)
      printf(" %d", arr[i]);
   printf("\n");
}

int main() {
   int *iArr1 = malloc(sizeof(int) * IDIM1);
   int *iArr2 = malloc(sizeof(int) * IDIM2);
   
   ScanInts(iArr1, IDIM1);
   ScanInts(iArr2, IDIM2);
   iArr1 = Insert(iArr1, iArr2, IDIM1, IDIM2, IDIM1 / 2);
   iArr2 = Insert(iArr2, iArr1, IDIM2, IDIM1 + IDIM2, IDIM2 / 2);

   PrintInts("iArr1", iArr1, IDIM1 + IDIM2);
   PrintInts("iArr2", iArr2, IDIM1 + 2 * IDIM2);
   
   free(iArr1);
   free(iArr2);
   printf("Final space: %ld\n", report_space());

   return 0;
}
