
/* Parallel version of quick sort */

#include<stdio.h>

#include<omp.h>

#include<stdio.h>


#define MEGA (1024*1024)

#define N (64*MEGA)


int a[N];

void Print_Array()

{
	
int i;
    
for( i=0;i<N;i++)
	 
printf("\t %d",a[i]);

}

void Init_Array()

{
	
int i;
	
/* Initializing the matrix */
	
for( i=0;i<N;i++)
		
a[i]=rand();

}


/*To overcome the overhead when threads are created, a small segment of code is implemented sequentially
 when there are less than 1000 elements in the array*/

void sequential_quicksort(int l,int r)
{
	
if(r>l)
{
		
int pivotIndex=a[r],t;
		
int low=l-1,high;
		
for(high=l;high<=r;high++)
{
			
if(a[high]<=pivotIndex)
{
				
low++;
				 
t=a[low];
				
a[low]=a[high];
				
a[high]=t;
			
}
		
}
		
sequential_quicksort(l,low-1);
		
sequential_quicksort(low+1,r);
	
}

}



/* Parallel version of quick sort */

void parallel_qsort(int l,int r)
{
	
if(r>l) /*Ensuring if there are elements in the array*/
{

		
int pivotIndex=a[r],t;
		
int low=l-1,high;
		
for(high=l;high<=r;high++)
{
			
if(a[high]<=pivotIndex)
{
				
low++;
				
t=a[low];
				
a[low]=a[high];
				
a[high]=t;
			
}

}
		
if((r-l)<1000)
{  
/* Implementing serial version of quick sort for lesser elements */
			
sequential_quicksort(l,low-1);
			
sequential_quicksort(low+1,r);
		
}
		
else
{
		
/* Quick Sort Parallelization */
		
/* Execution of two sub-arrays in parallel */
			
#pragma omp task
			
parallel_qsort(l,low-1);
			
#pragma omp task
			
parallel_qsort(low+1,r);
			
#pragma omp taskwait
		
}
	
}

}

int main()
{
	
int i;


Init_Array();

  
/* Printing initial array */
 
// printf("Pre Sort: \n");
 
// Print_Array();

  
/*Ensuring that the function is called only by 1 thread */
  
#pragma omp parallel	
	
#pragma omp single
	
parallel_qsort(0,N-1);
	
	
/* Printing the sorted array */	
	
//printf("Post Sort: \n");
	
//Print_Array();
		
return 0;

}