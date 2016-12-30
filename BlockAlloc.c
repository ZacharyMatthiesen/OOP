#include <stdio.h>
#include <SmartAlloc.h> // #include "SmartAlloc.h" if not on IHS

#define BUFFSIZE 16
#define NUM_STRUCTS 3

typedef struct {
   void *v0;
   char *cp0;
} SomeStruct;

void FreeAll(SomeStruct *data) {
   SomeStruct *temp, *old;
   


}

int main() {
   SomeStruct data[NUM_STRUCTS];
   SomeStruct *curStruct;
   SomeStruct *tempStruct = NULL;

   char *cp0 = malloc(BUFFSIZE);
   char c = '\0';

   data[0].v0 = &data[1];
   data[0].cp0 = malloc(BUFFSIZE);

   curStruct = data[1].v0 = malloc(sizeof(SomeStruct)); // This is a chained assignment. curStruct is set to the same value as data[1].v0 in this line.
   curStruct->cp0 = cp0;
   curStruct = curStruct->v0 = malloc(sizeof(SomeStruct));
   curStruct->cp0 = cp0;
   curStruct = curStruct->v0 = malloc(sizeof(SomeStruct));
   curStruct->v0 = curStruct;
   curStruct->cp0 = malloc(sizeof(int));

   curStruct = data[2].v0 = malloc(sizeof(SomeStruct));
   curStruct->v0 = tempStruct;
   curStruct->cp0 = &c;
   data[2].cp0 = malloc(10);

   FreeAll(data);

   printf("Final space: %ld\n", report_space());

   return 0;
}
