package com.tao.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class ZoneTable {
    private List<Zone> table = new ArrayList<>(16);
    private Vector<String> titleName = new Vector<>();

    public ZoneTable(int size) {
        titleName.add("区号");
        titleName.add("起始");
        titleName.add("大小");
        titleName.add("状态");
        titleName.add("作业");
        // 初始化的时候清空table
        table.clear();
        table.add(Zone.init(size));
    }

    public boolean add(Job job) {
        boolean success = false;
        if (job.getSize() <= 0) {
            return false;
        }
        for (Zone z : table) {
            if (z.getState().equals(State.FREE) && z.getSize() >= job.getSize()) {
                success = true;
                table.add(z.allot(job));
                if (z.getSize() == 0) {
                    table.remove(z);
                }
                break;
            }
        }
        return success;
    }

    public boolean free(int no) {
        if (no < 1 || no > table.size()) {
            return false;
        }
        Zone target = table.get(no - 1);
        if (!target.getState().equals(State.FREE)) {
            target.free();
            Zone pre = null;
            Zone next = null;

            if (no != 1) {
                pre = table.get(no - 2);
            }
            if (no != table.size()) {
                next = table.get(no);
            }
            if (pre != null && pre.getState().equals(State.FREE)) {
                pre.merge(target);
                table.remove(target);
                if (next != null && next.getState().equals(State.FREE)) {
                    pre.merge(next);
                    table.remove(next);
                }
            } else if (next != null && next.getState().equals(State.FREE)) {
                next.merge(target);
                table.remove(target);
            }
        } else {
            return false;
        }
        return true;
    }

    private void resetNo() {
        for (int i = 0; i < table.size(); i++) {
            table.get(i).setNo(i + 1);
        }
    }

    public void updateData(Vector data) {
        table = table.stream().sorted(Comparator.comparing(Zone::getNo)).collect(Collectors.toList());
        resetNo();
        data.clear();
        for (Zone zone : table) {
            Vector temp = new Vector();
            temp.add(zone.getNo());
            System.out.println(zone.getNo());
            temp.add(zone.getBegin());
            temp.add(zone.getSize());
            temp.add(zone.getState().getState());
            if (zone.getState().equals(State.BUSY)) {
                temp.add(zone.getJob().getName());
            } else {
                temp.add("无作业");
            }
            System.out.println("no=" + zone.getNo() + "begin=" + zone.getBegin() + "size=" + zone.getSize() + "sate=" + zone.getState().getState());
            data.add(temp);
        }
        System.out.println();
        System.out.println();
    }

    public Vector<String> getTitleName() {
        return titleName;
    }
}
