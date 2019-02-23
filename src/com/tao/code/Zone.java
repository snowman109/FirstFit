package com.tao.code;

public class Zone {
    private Integer no; // 区号
    private State state; // 状态
    private Integer begin; // 开始
    private Integer size; // 大小
    private Job job; // 作业

    private Zone(Integer no, State state, Integer begin, Integer size, Job job) {
        this.no = no;
        this.state = state;
        this.begin = begin;
        this.size = size;
        this.job = job;
    }


    public static Zone init(Integer size) {
        return new Zone(1, State.FREE, 0, size, null);
    }

    public Zone allot(Job job) {
        if (this.state.equals(State.BUSY) || this.size < job.getSize()) {
            return null;
        }
        Zone z = new Zone(no, State.BUSY, begin, job.getSize(), job);
        no = no + 1;
        size = size - job.getSize();
        begin = begin + job.getSize();
        return z;
    }

    public void merge(Zone zone) {
        if (this.no > zone.no) {
            this.begin = zone.getBegin();
        }
        this.size = this.size + zone.getSize();
    }

    protected void setNo(Integer no) {
        this.no = no;
    }

    public void free() {
        this.state = State.FREE;
        this.job = null;
    }

    public Integer getNo() {
        return no;
    }

    public State getState() {
        return state;
    }

    public Integer getBegin() {
        return begin;
    }

    public Integer getSize() {
        return size;
    }

    public Job getJob() {
        return job;
    }
}
