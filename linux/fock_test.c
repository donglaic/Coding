#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

int main(void)
{
	pid_t pid;
	int var = 1024;
	pid = fork();
	var = getpid();

	if (pid < 0) {
		printf("fork error");
		exit(1);
	} else if (pid == 0) {
		/* child process */
		printf("child: %d\n", var);
	} else {
		/* parent process */
		sleep(1);
		printf("parent: %d child: %d\n", var, pid);
	}

	return 0;
}
