#include <stdio.h>
#include <pthread.h>
#include <unistd.h> //for sleep

void *fun1(void) 
{ 
		int i; 
		for (i=0;i<5;i++)
		{
				printf("fun1 is running %d \n",i);
				sleep(1);
		}
		return NULL;
}

void *fun2(void) 
{ 
		int i; 
		for (i=0;i<5;i++)
		{
				printf("fun2 is running %d \n",i);
				sleep(1);
		}
		return NULL;
}

void *fun3(void) 
{ 
		int i; 
		for (i=0;i<5;i++)
		{
				printf("fun3 is running %d \n",i);
				sleep(1);
		}
		return NULL;
}

int main()
{
		int i=0,ret=0;
		pthread_t fun1_id,fun2_id,fun3_id;

		ret = pthread_create(&fun1_id,NULL,(void *)fun1,NULL);
		if (ret)
		{
				printf("Cannot create fun1.\n");
				return 1;
		}


		ret = pthread_create(&fun2_id,NULL,(void *)fun2,NULL);
		if (ret)
		{
				printf("Cannot create fun2.\n");
				return 1;
		}


		ret = pthread_create(&fun3_id,NULL,(void *)fun3,NULL);
		if (ret)
		{
				printf("Cannot create fun3.\n");
				return 1;
		}

		//wait for fun3.
		pthread_join(fun3_id,NULL);
		printf("Main thread exists.\n");

		return 0;
}

