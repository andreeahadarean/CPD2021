// FoxAlgorithm.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include "mpi.h"
#include <stdio.h>
#include <conio.h>
#include <stdlib.h>
#include <time.h>

#define N 100
#define M 4
#define MATRIX_SIZE 110

void multiply(int size, int A[], int B[], int C[])
{
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			C[i * size + j] = 0;
			for (int k = 0; k < size; k++) {
				C[i * size + j] += A[i * size + k] * B[k * size + j];
			}
		}
	}
}

void add(int size, int A[], int B[], int C[])
{
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			C[i * size + j] = A[i * size + j] + B[i * size + j];
		}
	}
}

void printMAtrix(int size, int A[MATRIX_SIZE * MATRIX_SIZE])
{
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			printf("%3d ", A[i * size + j]);
		}
		printf("\n");
	}
}

int main(int argc, char *argv[])
{
	int rank, worldSize;
	double startTime, endTime;

	int A[MATRIX_SIZE * MATRIX_SIZE];
	int B[MATRIX_SIZE * MATRIX_SIZE];
	int Cf[MATRIX_SIZE * MATRIX_SIZE];
	int Ci[MATRIX_SIZE * MATRIX_SIZE];
	int i, j;

	MPI_Status status;
	MPI_Init(NULL, NULL);
	MPI_Comm_size(MPI_COMM_WORLD, &worldSize);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	startTime = MPI_Wtime();
	if (rank == 0) {
		srand(time(NULL));
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				A[i * N + j] = rand() % MATRIX_SIZE;
				if (i == j)
					B[i * N + j] = 1;
				else B[i * N + j] = 0;
			}
		}
	}

	MPI_Bcast(A, N * N, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Bcast(B, N * N, MPI_INT, 0, MPI_COMM_WORLD);

	int R = N / M;

	int s[MATRIX_SIZE * MATRIX_SIZE], r[MATRIX_SIZE * MATRIX_SIZE];
	int p[MATRIX_SIZE * MATRIX_SIZE], q[MATRIX_SIZE * MATRIX_SIZE];

	for (i = 0; i < R; i++) {
		for (j = 0; j < R; j++) {
			s[i * R + j] = 0;
		}
	}

	int row = rank / M;
	int col = rank % M;
	int index = 0, l, C;

	for (index = 0; index < M; index++) {
		for (l = row * R; l < (row + 1) * R; l++) {
			for (C = index * R; C < (index + 1) * R; C++) {
				p[(l - (row * R)) * (R)+C - (index * R)] = A[l * N + C];
			}
		}

		for (l = index * R; l < (index + 1) * R; l++) {
			for (C = col * R; C < (col + 1) * R; C++) {
				q[(l - (index * R)) * (R)+C - (col * R)] = B[l * N + C]; \
			}
		}

		multiply(R, p, q, r);
		add(R, s, r, s);
	}

	MPI_Barrier(MPI_COMM_WORLD);
	endTime = MPI_Wtime();

	MPI_Gather(s, R * R, MPI_INT, Ci, R * R, MPI_INT, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		for (i = 0; i < N; i++) {
			for (j = 0; j < N; j++)
			{
				int index = i * N + j;
				int block = index / (R * R);
				int rowBlock = block / M;
				int finalRow = rowBlock * R + (index % (R * R)) / R;

				int colBlock = block % M;
				int finalCol = colBlock * R + (index % (R * R)) % R;

				Cf[finalRow * N + finalCol] = Ci[index];
			}
		}

		//printf("REZULTAT!\n");
		multiply(N, A, B, Cf);
		//printMAtrix(N, Cf);
		printf("Runtime = %f\n", endTime - startTime);
	}

	MPI_Finalize();
	return 0;
}