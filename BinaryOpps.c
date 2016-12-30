#include <stdio.h>
#include <string.h>

#define NUM_SIZE 100
#define DIGIT_ONE '1'
#define DIGIT_ZERO '0'

//control -d is your way of saying that the read call should return zero
void TwosComplement(char *twoscomp) {
  int carry;
   while ((*twoscomp++) != 0) { 
      if (*twoscomp == '0') {
         *twoscomp = '1';
      }
      else {
         *twoscomp = '0'; 
      }
   }
   if (*twoscomp == '0') {
      *twoscomp = '1';
   }
   else {
      *twoscomp = '0';
      carry = 1;
   }
   while ((*twoscomp++) != 0 && carry == 1) {
      if (*twoscomp == '0') {
         *twoscomp = '1';
	 carry = 0;
      }
      else {
         *twoscomp = '0';
         carry = 1;
      }	
   }	
}


int main() {
   char number[NUM_SIZE];
   
   while (EOF != scanf("%99s", number)) {
      TwosComplement(number);
      printf("%s\n", number);
   }

   return 0;
}
