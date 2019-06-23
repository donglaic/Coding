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

	//input text
	printf("input text to send: ");
	fgets(message.content, 100, stdin);
	message.type = 1;

	//send data
	msgsnd(mqid,&message,sizeof(message),0);
	printf("data sent: %s", message.content);

	return 0;
}
	
