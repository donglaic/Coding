#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <string.h>
#include <unistd.h>


#define ANSI_COLOR_CYAN		"\x1b[36m"
#define ANSI_COLOR_RESET	"\x1b[0m"

void *create_shared_memory(size_t size)
{
	//set shared mem rw
	int protection = PROT_READ | PROT_WRITE;

	//set shared mem shared (3th process readable) and anonymous (3th process does not known address)
	int visibility = MAP_ANONYMOUS | MAP_SHARED;

	//create shared mem
	return mmap(NULL,size,protection,visibility,0,0);
}

int main()
{
	setbuf(stdout,NULL);
	void *shmem =create_shared_memory(128);
	int pid = fork();

	if (pid == 0)
	{
		while (1)
		{
			char message[100];
			printf("input text to send: \n");
			fgets(message,100,stdin);
			memcpy(shmem,message,sizeof(message));
			printf("child process write data succ: %s\n",shmem);
		}
	}
	else
	{
		while(1)
		{
			printf(ANSI_COLOR_CYAN "data in parent process`s memory: %s\n" ANSI_COLOR_RESET, shmem);
			sleep(1);
		}
	}
}
