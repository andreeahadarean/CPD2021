#include <iostream>
#include <omp.h>

#define SIZE 3

void printMatrix(float A[SIZE][SIZE]) {
    for(int i = 0; i < SIZE; i++) {
        for(int j = 0; j < SIZE; j++) {
            printf("%f ", A[i][j]);
        }
        printf("\n");
    }
}


int main() {
    float A[SIZE][SIZE];
    float B[SIZE][SIZE];
    float res[SIZE][SIZE];

    int i, j, k, tid;
    float total = 0.0;

    float val = 1; 
    for(int i = 0; i < SIZE; i++) {
        for(int j = 0; j < SIZE; j++) {
            A[i][j] = val;
            B[i][j] = val;
            val++; 
        }
    }

    printf("Matrix A\n");
    printMatrix(A);
    printf("Matrix B\n");
    printMatrix(B);

#pragma omp parallel shared(A, B, res, total) private(tid, i)
    {
        tid = omp_get_thread_num();
#pragma omp for private(j, k)
        for(i = 0; i < SIZE; i++) {
           // printf("I'm thread %d in loop i = %d\n", omp_get_thread_num(), i);
            for(j = 0; j < SIZE; j++) {
                //printf("I'm thread %d in loop j = %d\n", omp_get_thread_num(), j);
                for(k = 0; k < SIZE; k++) {
                    //printf("I'm thread %d in loop k = %d\n", omp_get_thread_num(), k);
                    total = total + A[i][k] * B[k][j];
                }
                res[i][j] = total;
                printf("Thread %d did row %d and col %d\n", tid, i, j);
                total = 0;
            }
        }
    }

    printf("Result: \n");
    printMatrix(res);

}