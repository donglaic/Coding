#include <stdio.h>
#include <sys/ipc.h>
#include <sys/msg.h>

struct msg_struct {
	int type;
	char content[100];
} message;

int main()
{
	//generate IPC key
	key_t key = ftok("path",65);

	//create a Message Queue, get the Message Queue ID
	int mqid = msgget(key, 0666 | IPC_CREAT);

	//recv data
	msgrcv(mqid,&message,sizeof(message),1,0);
	printf("recv sent: %s", message.content);

	//destroy msg
	msgctl(mqid,IPC_RMID,NULL);

	return 0;
}
	
