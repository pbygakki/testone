#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <iostream.h>
//��Ҫ������

char getSignal();                      //��ȡ������������
int random(double,double);             //��ȡ���������
int getResult(int,int,int,char,char);           //������㺯��
int takeTest();                        //��Ŀ���ɺ���


//������

void main() 
{
         int n,a,right=0;
         double percent;//��ȷ��
		 float score;//����
         printf("Please enter the number of the test:");//������Ҫ���ɵ���Ŀ����
         scanf("%d",&n);
         for(int i=0;i<n;i++)
         {
                   a=takeTest();
                   right=right+a;
         }
         printf("You finish all test��\n");
         printf("The number of right:%d\n",right);
         percent=((double)right*100.00)/(double)n;
		 score=percent;
         printf("The percent of right:%0.2f %%\n",percent);
		 printf("The score of right:%0.2f %\n",score);
}

//��ȡ��������������

char getSignal()
{
    char signal[4]={'+','-','*','/'};
    return signal[rand()%4];
}

//��ȡ�����������

int random(double start, double end)
{
    return (int)(start+(end-start)*rand()/(RAND_MAX+ 1.0));
}

//������㺯����

int getResult(int num1,int num2,int num3,char signal,char signal2)
{
    int res;
    switch(signal)
    {
    case '+':  //��һ�������Ϊ�Ӻŵ�����
		switch(signal2){
		case '+':
			res=num1+num2+num3;break;
		case '-':
			res=num1+num2-num3;break;
		case '*':
			res=num1+num2*num3;break;
		case '/':
			res=num1+num2/num3;break;
		}
	break;
    case '-':       //��һ�������Ϊ���ŵ�����
        switch(signal2){
		case '+':
        res=num1-num2+num3;break;
		case '-':
        res=num1-num2-num3;break;
		case '*':
        res=num1-num2*num3;break;
		case '/':
        res=num1-num2/num3;break;
		}
	break;
    case '*':      //��һ�������Ϊ�˺ŵ�����
        switch(signal2){
		case '+':
        res=num1*num2+num3;break;
		case '-':
        res=num1*num2-num3;break;
		case '*':
        res=num1*num2*num3;break;
		case '/':
	    res=num1*num2/num3;break;
		}
	break;
    case '/':      //��һ�������Ϊ���ŵ�����
        switch(signal2){
		case '+':
        res=num1/num2+num3;break;
		case '-':
        res=num1/num2-num3;break;
		case '*':
        res=num1/num2*num3;break;
		case '/':
        res=num1/num2/num3;break;
		}
	break;
	 default:
        printf("���������\n");
    }
    return res;
}

//��Ŀ���ɺ�����

int takeTest()
{
    int get;
    int num1,num2,num3,a;
    char signal,signal2;

    srand((unsigned)time(NULL));//���������
    signal2=getSignal();//����������ĵ��ú���
	signal=getSignal();
    num1=random(1,100);
    num2=random(1,100);
	num3=random(1,100);
	
    printf("%d%c%d%c%d=",num1,signal,num2,signal2,num3);
    scanf("%d",&get);
    fflush(stdin);   //������뻺����
    if(getResult(num1,num2,num3,signal,signal2)==get)//���������Լ�����Ľ�����Ƚ�
    {
        printf("You're right!\n");
        a=1;
    }
    else
    {
        printf("It's wrong!\n");
        printf("The right answer is: %d\n",getResult(num1,num2,num3,signal,signal2));
        a=0;
    }
    return a;
}

