#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <iostream.h>
//主要函数：

char getSignal();                      //获取随机运算符函数
int random(double,double);             //获取随机数函数
int getResult(int,int,int,char,char);           //结果计算函数
int takeTest();                        //题目生成函数


//主函数

void main() 
{
         int n,a,right=0;
         double percent;//正确率
		 float score;//分数
         printf("Please enter the number of the test:");//输入你要生成的题目数量
         scanf("%d",&n);
         for(int i=0;i<n;i++)
         {
                   a=takeTest();
                   right=right+a;
         }
         printf("You finish all test！\n");
         printf("The number of right:%d\n",right);
         percent=((double)right*100.00)/(double)n;
		 score=percent;
         printf("The percent of right:%0.2f %%\n",percent);
		 printf("The score of right:%0.2f %\n",score);
}

//获取随机运算符函数：

char getSignal()
{
    char signal[4]={'+','-','*','/'};
    return signal[rand()%4];
}

//获取随机数函数：

int random(double start, double end)
{
    return (int)(start+(end-start)*rand()/(RAND_MAX+ 1.0));
}

//结果计算函数：

int getResult(int num1,int num2,int num3,char signal,char signal2)
{
    int res;
    switch(signal)
    {
    case '+':  //第一个运算符为加号的运算
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
    case '-':       //第一个运算符为减号的运算
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
    case '*':      //第一个运算符为乘号的运算
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
    case '/':      //第一个运算符为除号的运算
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
        printf("运算符错误！\n");
    }
    return res;
}

//题目生成函数：

int takeTest()
{
    int get;
    int num1,num2,num3,a;
    char signal,signal2;

    srand((unsigned)time(NULL));//随机数种子
    signal2=getSignal();//两个运算符的调用函数
	signal=getSignal();
    num1=random(1,100);
    num2=random(1,100);
	num3=random(1,100);
	
    printf("%d%c%d%c%d=",num1,signal,num2,signal2,num3);
    scanf("%d",&get);
    fflush(stdin);   //清空输入缓冲区
    if(getResult(num1,num2,num3,signal,signal2)==get)//运算结果和自己输入的结果作比较
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

