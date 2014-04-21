package io.afield.roodle;

public class Timer
{
    protected float remaining;
    protected float interval;

    public Timer(float interval){
    	
        this.interval = interval;
        this.remaining = interval;
    }


	public boolean hasTimeElapsed() { 
    	return (remaining < 0.0f); 
    }

    public void reset() { 
    	remaining = interval; 
    }

    public void reset(float interval) {
        this.interval = interval;
        this.remaining = interval;
    }

    public void update(float deltaTime) { 
    	remaining -= deltaTime; 
    }
}	
