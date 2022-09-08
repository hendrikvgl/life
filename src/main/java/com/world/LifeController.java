package com.world;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LifeController {
    LifeManager lm;
    public long tick;

    public LifeController(LifeManager lm) {
        this.lm = lm;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tick = tick + App.TICKRATE;
                long startTime = System.nanoTime();
                for (Life l : lm.getLifes()) {
                    l.onTick(App.TICKRATE);

                    if (!l.idle) {
                        continue;
                    }

                    double dF = l.fitness - l.prevFitness;

                    LinkedList<Memory> mem = l.memory;
                    if (mem.size() > 0) {
                        Memory lastMem = mem.getLast();
                        lastMem.setFitness(dF);
                        LinkedList<Memory> buffer = new LinkedList<Memory>();
                        for (Memory cm : mem) {
                            if (cm.param != lastMem.param || cm.activity != lastMem.activity) {
                                if (cm.fitness > 0) {
                                    buffer.add(cm);
                                }
                            }
                        }
                        mem = buffer;

                    }

                    Collections.sort(mem, new Comparator<Memory>() {
                        @Override
                        public int compare(Memory lhs, Memory rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return lhs.fitness < rhs.fitness ? -1 : (lhs.fitness > rhs.fitness) ? 1 : 0;
                        }
                    });

                    double CHAOS = App.rng.nextDouble();
                    l.prevFitness = l.fitness;

                    int d = App.rng.nextInt(4);
                    Memory newMem = new Memory(0, d);

                    if (CHAOS > 0.95 || mem.size() == 0) {
                        d = l.checkMove(d);
                        l.move(d);
                        l.memory.add(new Memory(0, d));
                        continue;
                    }

                    int act = 0;
                    int par = d;

                    if (mem.size() > 0) {
                        double[] fit = new double[mem.size()];
                        double sum = 0;
                        int index = 0;
                        for (Iterator<Memory> i = mem.iterator(); i.hasNext();) {
                            Memory item = i.next();
                            // System.out.println("p: " + item.param + "; f: " + item.fitness);
                            fit[index] = item.fitness;
                            sum += item.fitness;
                            index++;
                        }
                        double r = App.rng.nextDouble(sum);
                        double ac = 0;
                        int ind = 0;
                        int tInd = 0;
                        for (double i : fit) {
                            if (r + ac <= i) {
                                tInd = ind;
                                break;
                            }
                            ind++;
                            ac += i;
                        }

                        Memory c = mem.get(tInd);
                        act = c.activity;
                        par = c.param;
                        newMem = c;
                    }

                    l.memory = mem;

                    switch (act) {
                    case 0:
                        int fdir = l.checkMove(par);
                        newMem.param = fdir;
                        l.move(fdir);
                        l.memory.add(newMem);
                    }
                }
                long endTime = System.nanoTime();
                long totalTime = endTime - startTime;
                System.out.println(lm.getLifes().size());
                // System.out.println(TimeUnit.MILLISECONDS.convert(totalTime,
                // TimeUnit.NANOSECONDS));
            }
        }, 0, App.TICKRATE);
    }
}
