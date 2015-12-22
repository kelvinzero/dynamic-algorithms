#include <stdlib.h>
#include <assert.h>
#include <stdio.h>
#include "Minheap.h"

#define TRUE 1;
#define FALSE 0;

int parent(int child);
int leftChild(int parent);
int rightChild(int parent);
int swap(Heap *heap, int a, int b);
void Heap_minHeapify(Heap *heap_array, int position);

Heap *Start_Heap(int size, int(*compare)(void* a, void* b)) {

    Heap *heap;
    heap = malloc(sizeof(Heap*));
    heap->heap_array = malloc(size * sizeof(void*));

    heap->max_size = size;
    heap->compare = compare;
    heap->size = 0;
    return heap;
}

void *Heap_pop(Heap *heap) {

    assert(heap != NULL);

    int *size;
    void **heap_array;

    heap_array = heap->heap_array;
    size = &heap->size;

    if(*size == 0){
        printf("Heap is empty!\n");
        return NULL;
    }
    else{
        void *return_element;
        return_element = heap_array[0];  // get the root
        swap(heap, 0, --(*size));  // move the last element to root position, decrement size
        Heap_minHeapify(heap, 0); // move the new root to the correct position
        return return_element;
    }
}

void Heap_minHeapify(Heap *heap, int position) {

        int left = leftChild(position);
        int right = rightChild(position);
        int smallest;
        void **heap_array = heap->heap_array;
        int (*comp)(void*, void*);
        comp = heap->compare;

        if (left < heap->size &&
                comp(heap_array[position], heap_array[left]) >= 0)
            smallest = left;
        else
            smallest = position;

        if (right < heap->size &&
                comp(heap_array[position], heap_array[right]) >= 0 &&
                comp(heap_array[left], heap_array[right]) >= 0)
            smallest = right;

        if (smallest != position) {
            swap(heap, position, smallest);
            Heap_minHeapify(heap, smallest);
        }
}

int Heap_add(Heap *heap, void *element) {

    assert(heap != NULL);

    int *size;
    int max_size;
    void **heap_array;

    heap_array = heap->heap_array;
    size = &heap->size;
    max_size = heap->max_size;

    if(*size == 0){
        heap_array[0] = element;
    }
    else if(*size < max_size){

        int comp;
        int curr;

        curr = *size;
        heap_array[curr] = element;

        comp = heap->compare(heap_array[curr], heap_array[parent(curr)]);

        while(comp < 0 && curr > 0){
            swap(heap, curr, parent(curr));
            curr = parent(curr);
            if(curr > 0)
                comp = heap->compare(heap_array[curr], heap_array[parent(curr)]);
        }
    }
    else{
        printf("Heap is full!\n");
        return FALSE;
    }
    *size = *size+1;
    return TRUE;
}

int parent(int child) {
    return ((child+1)/2-1);
}

int leftChild(int parent) {
    return ((parent+1)*2-1);
}

int rightChild(int parent) {
    return ((parent+1)*2);
}

int swap(Heap *heap, int a, int b) {

    if(a < 0 || b < 0)
        return FALSE;

    void *temp;
    temp = heap->heap_array[a];
    heap->heap_array[a] = heap->heap_array[b];
    heap->heap_array[b] = temp;
    return TRUE;
}
