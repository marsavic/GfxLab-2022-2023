## Animations

### Workers

Workers are animations that compute iterations inside an infinite loop.

- Workers have to be registered with the app, so that they all can be controlled (started/paused/disposed) from outside an animation.
  - Ideally, the app will provide that infinite loop, and a the worker will just provide the action to be performed inside.


### Pulling

Pullers should be the only type of workers?

`PullingCache`
- A worker with last frame cached and immediately available to the caller. 


### Buffering

- Buffer manager provides matrix buffers per request.
  - Has a set of matrices, reuses them.
- Clients work with buffers only inside a callback block.
- The caller needs to provide a buffer to be filled by the called animation.
  - Benefit: same buffer can propagate through several layers. 
- Buffer manager sets private data field inside Matrix buffer to null when the block is exited.
  - If anyone saved the reference to the Matrix it will now be useless, throwing a NPE if trying to read/write to it.

What to do with animations needing to store matrices as fields?
  - Well, are they really replacing these matrices or just overwriting them?


### Synchronization

Animation T might need to work on the sources A and B obtained from the same iteration of S.


```
  A   
 / \  
S   T 
 \ /  
  B   
```

Keep a history of *touches* at every frame. The history is a map (animation -> iteration number) containing all the animations that processed the frame.

If synchronization is needed (at T), wait until all the sources (frames from A and B) have the same iteration number at the wanted animation (S).

How to unite histories at T?
- Keep only entries that are the same in A and B.

## Graphics

### Noise removal

1. Make ray tracers return detailed info about each pixel: body, normal, uv.
1. Make a matrix containing the ratio between the obtained color and the texture color at (body, uv) for each pixel.
1. Blur that matrix, observing thresholds based on (body, normal, uv). 
1. Recompute the colors by multiplying the texture color with blurred value.

- Be adaptive, reduce the blur radius if there is less noise.
  - Not only per frame, but per pixel?

* Alas, what shell we do with lens blur? 