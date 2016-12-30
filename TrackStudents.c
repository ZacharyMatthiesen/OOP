#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>

typedef struct {
   int id;
   char name[16];
   double gpa;
} Student;

int main(int argc, char **argv) {
   int res, offs, newSize, fd; 
   Student st1, st2;
   char cmd[10];
   struct stat stats;
   
   if (argc != 2)
      fprintf(stderr, "Usage: TrackStudents dbFile\n");
   else if ((fd = open(argv[1], O_CREAT|O_RDWR, 0644)) < 0)
      fprintf(stderr, "Cannot open %s: %s\n", argv[1], strerror(errno));
   else {
      fstat(fd, &stats);
      printf("Database %s has %d students\n", argv[1],
       stats.st_size/sizeof(Student));

      while (EOF != scanf("%s %d", cmd, &st1.id))
         if (!strcmp(cmd, "add")) {
            scanf("%s %lf", st1.name, &st1.gpa);
            lseek(fd, 0, SEEK_END);
            write(fd, &st1, sizeof(Student));
         }
         else if (!strcmp(cmd, "find") || !strcmp(cmd, "drop")) {
            lseek(fd, 0, SEEK_SET);
            do {
               res = read(fd, &st2, sizeof(Student));
            } while (res != 0 && st2.id != st1.id);
            if (res == 0)
               printf("There is no student with id %d\n", st1.id);
            else if (!strcmp(cmd, "find")) 
               printf("%d: %s with gpa %0.2f\n", st2.id, st2.name, st2.gpa);
            else {
               offs = lseek(fd, 0, SEEK_CUR) - sizeof(Student);
               newSize = lseek(fd, -sizeof(Student), SEEK_END);
			   read(fd, &st1, sizeof(Student));
               lseek(fd, offs, SEEK_SET);
               write(fd, &st1, sizeof(Student));
               ftruncate(fd, newSize);
            }
         }
      close(fd);
   }
}
