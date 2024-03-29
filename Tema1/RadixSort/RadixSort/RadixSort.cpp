// RadixSort.cpp : This file contains the 'main' function. Program execution begins and ends there.
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

int getMax(int arr[], int n)
{
	int mx = arr[0];
	for (int i = 1; i < n; i++)
		if (arr[i] > mx)
			mx = arr[i];
	return mx;
}

void countSort(int arr[], int n, int exp)
{
	int* output = (int*)malloc(n * sizeof(int)); 
	int i, count[10] = { 0 };

	for (i = 0; i < n; i++)
		count[(arr[i] / exp) % 10]++;

	for (i = 1; i < 10; i++)
		count[i] += count[i - 1];

	for (i = n - 1; i >= 0; i--) {
		output[count[(arr[i] / exp) % 10] - 1] = arr[i];
		count[(arr[i] / exp) % 10]--;
	}

	for (i = 0; i < n; i++)
		arr[i] = output[i];
}

void radixSortSequencial(int arr[], int n)
{
	int exp; 
	int m = getMax(arr, n);

	for (exp = 1; m / exp > 0; exp *= 10)
		countSort(arr, n, exp);
}

void radixSortParallel(int arr[], int n)
{
	int exp;
	int m = getMax(arr, n);
	int tid;
#pragma omp parallel num_threads(maxNrOfThreads) shared(m) private(tid, exp)
	{
		tid = omp_get_thread_num();

		for (exp = 1; m / exp > 0; exp *= 10)
#pragma omp critical
		{
			countSort(arr, n, exp);
		}
	}
}

void print(int arr[], int n)
{
	for (int i = 0; i < n; i++)
		cout << arr[i] << " ";
}

int main()
{
	/*int arr[] = { 170, 45, 75, 90, 802, 24, 2, 66 };
	int n = sizeof(arr) / sizeof(arr[0]);

	radixSortParallel(arr, n);
	print(arr, n);*/

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

			radixSortParallel(sortedArray, i);

			endTime = omp_get_wtime();

			printf("%f \n", endTime - startTime);
		}

		free(sortedArray);
		free(generatedArray);

	}
	return 0;
}
