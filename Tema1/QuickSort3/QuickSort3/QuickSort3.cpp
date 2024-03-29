// QuickSort3.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <omp.h>
#include <time.h>

using namespace std;

#define MAX_NUMBER 10000000

int maxNrOfThreads; 

void generateArray(int* a, int n) {

	srand(time(NULL));
	for (int i = 0; i < n; ++i)
		a[i] = rand() % MAX_NUMBER;
}

void swap(int* a, int* b) {
	int t = *a;
	*a = *b;
	*b = t;
}

int partition(int arr[], int low, int high)
{
	int pivot = arr[high]; 
	int i = (low - 1); 

	for (int j = low; j <= high - 1; j++)
	{ 
		if (arr[j] < pivot)
		{
			i++; 
			swap(&arr[i], &arr[j]);
		}
	}
	swap(&arr[i + 1], &arr[high]);
	return (i + 1);
}

void quickSortSequential(int arr[], int low, int high)
{
	if (low < high)
	{
		int pi = partition(arr, low, high);
		quickSortSequential(arr, low, pi - 1);
		quickSortSequential(arr, pi + 1, high);
	}
}

void quickSortParallel(int arr[], int low, int high, int currentNrOfThreads)
{
	if (currentNrOfThreads >= maxNrOfThreads)
		quickSortSequential(arr, low, high);
	else
	{
		int pi = partition(arr, low, high);
#pragma omp parallel sections num_threads(2) if (maxNrOfThreads > currentNrOfThreads)
		{
#pragma omp section
			quickSortParallel(arr, low, pi - 1, currentNrOfThreads * 2);

#pragma omp section
			quickSortParallel(arr, pi + 1, high, currentNrOfThreads * 2);
		}
	}
}

void printArray(int arr[], int size)
{
	int i;
	for (i = 0; i < size; i++)
		cout << arr[i] << " ";
	cout << endl;
}

int main()
{
	/*int arr[] = { 10, 7, 8, 9, 1, 5 , 17, 20, 130, 25};
	int n = sizeof(arr) / sizeof(arr[0]);
	quickSortParallel(arr, 0, n - 1);
	cout << "Sorted array: \n";
	printArray(arr, n);*/

	omp_set_nested(1);
	omp_set_dynamic(0);
	
	double startTime;
	double endTime;

	for (int i = 100000; i <= MAX_NUMBER; i *= 2)
	{
		printf("Array size: %d\n", i);

		int* generatedArray = (int*)malloc(i * sizeof(int));
		int* sortedArray = (int*)malloc(i * sizeof(int));

		generateArray(generatedArray, i);

		for (maxNrOfThreads = 1; maxNrOfThreads <= 8; maxNrOfThreads *= 2)
		{
			memcpy(sortedArray, generatedArray, sizeof(int) * i);

			printf("Number of threads: %d : ", maxNrOfThreads);

			startTime = omp_get_wtime();

			quickSortParallel(sortedArray, 0, i - 1, 1);

			endTime = omp_get_wtime();

			printf("%f \n", endTime - startTime);
		}

		free(sortedArray);
		free(generatedArray);

	}
	return 0;
}
