#ifndef MINHEAP_H
#define MINHEAP_H

typedef struct node_struct Node;
typedef struct heap_struct Heap;
Heap * Start_Heap(int size, int(*compare)(void* a, void* b));
int Heap_add(Heap *heap, void *element);
void * Heap_pop(Heap *heap);

struct heap_struct{
    int max_size;
    int size;
    void **heap_array;
    int (*compare)(void* a, void* b);
};

#endif //MINHEAP_H
