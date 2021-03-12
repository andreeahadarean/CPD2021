#include <iostream>
#include <omp.h>

using namespace std;

int main() {
    #pragma omp parallel
    {
       printf("Hello World from thred %d!\n", omp_get_thread_num());
    }
    return 0;
}