// MergeSort.cpp : This file contains the 'main' function. Program execution begins and ends there.
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

void merge(int arr[], int l, int m, int r)
{
	int n1 = m - l + 1;
	int n2 = r - m;

	int *L = (int*)malloc(n1 * sizeof(int));
	int *R = (int*)malloc(n2 * sizeof(int));

	for (int i = 0; i < n1; i++)
		L[i] = arr[l + i];
	for (int j = 0; j < n2; j++)
		R[j] = arr[m + 1 + j];

	int i = 0;

	int j = 0;

	int k = l;

	while (i < n1 && j < n2) {
		if (L[i] <= R[j]) {
			arr[k] = L[i];
			i++;
		}
		else {
			arr[k] = R[j];
			j++;
		}
		k++;
	}

	while (i < n1) {
		arr[k] = L[i];
		i++;
		k++;
	}

	while (j < n2) {
		arr[k] = R[j];
		j++;
		k++;
	}
}


void mergeSortSequential(int arr[], int l, int r) {
	if (l >= r) {
		return;
	}
	int m = l + (r - l) / 2;
	mergeSortSequential(arr, l, m);
	mergeSortSequential(arr, m + 1, r);
	merge(arr, l, m, r);
}


void mergeSortParallel(int arr[], int l, int r, int currentNrOfThreads) {
	if (l >= r) {
		return;
	}
	int m = l + (r - l) / 2;
#pragma omp parallel sections num_threads(2) if (maxNrOfThreads > currentNrOfThreads) 
	{
#pragma omp section
		mergeSortParallel(arr, l, m, currentNrOfThreads * 2);
#pragma omp section
		mergeSortParallel(arr, m + 1, r, currentNrOfThreads * 2);
	}
	merge(arr, l, m, r);
}

void printArray(int A[], int size)
{
	for (int i = 0; i < size; i++)
		cout << A[i] << " ";
}

int main()
{
	/*int arr[] = { 12, 11, 13, 5, 6, 7 };
	int arr_size = sizeof(arr) / sizeof(arr[0]);
	cout << "Given array is \n";
	printArray(arr, arr_size);
	mergeSortSequential(arr, 0, arr_size - 1);
	cout << "\nSorted array is \n";
	printArray(arr, arr_size);*/

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

			mergeSortParallel(sortedArray, 0, i - 1, 1);

			endTime = omp_get_wtime();

			printf("%f \n", endTime - startTime);
		}

		free(sortedArray);
		free(generatedArray);

	}
	return 0;
}

