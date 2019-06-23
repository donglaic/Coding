#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main()
{
	pid_t pid = fork();
	if (pid < 0)
	{
		printf("error");
	}
	else if (pid == 0) /*child process*/
	{
		int sum = 0;
		for (int i = 0; i<1000000;i++)
		{
			for (int j=0;j<100;j++)
			{
				sum += i;
			}
		}
		exit(0);
	}
	else /*parent process*/
	{
		int status;
		printf("child process pid %d...\n", pid);
		//wait child process end
		do
		{
			waitpid(pid,&status,WUNTRACED);
		} while (!WIFEXITED(status) && !WIFSIGNALED(status));

		//satus is return value of child process
		printf("child process return %d\n", status);
	}
}



