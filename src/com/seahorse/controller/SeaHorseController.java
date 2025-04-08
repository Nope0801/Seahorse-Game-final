package com.seahorse.controller;

import com.seahorse.model.Board;
import com.seahorse.model.SeaHorse;
import com.seahorse.utils.SeaHorseState;
import com.seahorse.utils.UpdateComponent;
import com.seahorse.view.SeaHorseView;
import java.util.ArrayList;
import java.util.List;

public class SeaHorseController implements UpdateComponent {
    public PlayerController player;
    private SeaHorse seaHorseData;
    private SeaHorseView seaHorseView;

    private int spawnPos[] = new int[2];
    public SeaHorseController(PlayerController _player, int x, int y) {
        UpdateComponent.AddUpdate(this);

        player = _player;
        seaHorseData = new SeaHorse(player.getColor());
        seaHorseData.setRelativeX(x);
        seaHorseData.setRelativeY(y);
        seaHorseData.setX(Board.changeRelativeCoordinates(x, y)[0]);
        seaHorseData.setY(Board.changeRelativeCoordinates(x, y)[1]);
        spawnPos[0] = x;
        spawnPos[1] = y;

        seaHorseView = new SeaHorseView();
        seaHorseView.setCurrentAnimation(seaHorseData.getSeaHorseIdleAnimation());
        seaHorseView.setPosition(x, y);
    }

    @Override
    public void Update() {
        //FLIP
        seaHorseView.flip = (seaHorseData.getX1() > seaHorseData.getX2());

        // if (seaHorseData.getState() == SeaHorseState.StartStep) {
        //     seaHorseData.setState(SeaHorseState.IsStep);
        // }

        if (seaHorseData.getState() == SeaHorseState.IsStep) {
            seaHorseData.setCurrentIndexOnLinePixels(seaHorseData.getCurrentIndexOnLinePixels() + seaHorseData.getPixelSpeed());
            seaHorseData.setX(seaHorseData.getLinePixels().get(seaHorseData.getCurrentIndexOnLinePixels())[0]);
            seaHorseData.setY(seaHorseData.getLinePixels().get(seaHorseData.getCurrentIndexOnLinePixels())[1]);
            
            if (seaHorseData.getX() == seaHorseData.getX2() && seaHorseData.getY() == seaHorseData.getY2()) {
                // seaHorseData.setX(seaHorseData.getX2());
                int spawnPosConvert[] = Board.changeRelativeCoordinates(spawnPos[0], spawnPos[1]);
                if (seaHorseData.getX2() == spawnPosConvert[0] && seaHorseData.getY2() == spawnPosConvert[1]) {
                    seaHorseData.setState(SeaHorseState.InStable);
                    seaHorseData.setStepLeft(0);
                    seaHorseView.setCurrentAnimation(seaHorseData.getSeaHorseIdleAnimation());
                }
                else {
                    EndStep();
                }
                // seaHorseData.setY(seaHorseData.getY2());
            }
        }

        seaHorseView.setPosition(seaHorseData.getX(), seaHorseData.getY());
    }
    
    public void Move(int step, int relativex2, int relativey2) {
        if (step == 0) {
            seaHorseView.setCurrentAnimation(seaHorseData.getSeaHorseIdleAnimation());
            seaHorseData.setStepLeft(0);
            return;
        }
        seaHorseData.setRelativeX(relativex2);
        seaHorseData.setRelativeY(relativey2);
        seaHorseData.setX1(seaHorseData.getX());
        seaHorseData.setY1(seaHorseData.getY());
        seaHorseData.setX2(Board.changeRelativeCoordinates(relativex2, relativey2)[0]);//chuyen doi relative sang real
        seaHorseData.setY2(Board.changeRelativeCoordinates(relativex2, relativey2)[1]);//chuyen doi relative sang real
        List<int[]> line = drawLinePixels(seaHorseData.getX1(), seaHorseData.getY1(), seaHorseData.getX2(), seaHorseData.getY2());
        seaHorseData.setLinePixels(line);
        seaHorseData.setCurrentIndexOnLinePixels(0);
        seaHorseData.setStepLeft(step);
        seaHorseView.setCurrentAnimation(seaHorseData.getSeaHorseMoveAnimation());
    }

    public void BackToStable() {
        seaHorseData.setRelativeX(spawnPos[0]);
        seaHorseData.setRelativeY(spawnPos[1]);
        seaHorseData.setX1(seaHorseData.getX());
        seaHorseData.setY1(seaHorseData.getY());
        seaHorseData.setX2(Board.changeRelativeCoordinates(spawnPos[0], spawnPos[1])[0]);//chuyen doi relative sang real
        seaHorseData.setY2(Board.changeRelativeCoordinates(spawnPos[0], spawnPos[1])[1]);//chuyen doi relative sang real
        List<int[]> line = drawLinePixels(seaHorseData.getX1(), seaHorseData.getY1(), seaHorseData.getX2(), seaHorseData.getY2());
        seaHorseData.setLinePixels(line);
        seaHorseData.setCurrentIndexOnLinePixels(0);
        seaHorseData.setStepLeft(1);
        seaHorseView.setCurrentAnimation(seaHorseData.getSeaHorseMoveAnimation());
    }

    public static List<int[]> drawLinePixels(int x1, int y1, int x2, int y2) {
        List<int[]> pixels = new ArrayList<int[]>();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            pixels.add(new int[]{x1, y1});

            if (x1 == x2 && y1 == y2) break;

            int e2 = 2 * err;
            if (e2 > -dy) { 
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) { 
                err += dx;
                y1 += sy;
            }
        }
        // System.out.println("Số pixel: " + pixels.size());
        return pixels;
    }

    public SeaHorseState getState() {
        return seaHorseData.getState();
    }

    public void setState(SeaHorseState state) {
        seaHorseData.setState(state);
    }

    public void StartSeaHorseAction() {
        seaHorseData.setState(SeaHorseState.StartStep);
    }

    public void EndStep() {
        seaHorseData.setStepLeft(seaHorseData.getStepLeft() - 1);
        seaHorseData.setState(SeaHorseState.EndStep);
        // System.out.println(seaHorseData.getStepLeft());
    }

    public void EndAction() {
        seaHorseView.setCurrentAnimation(seaHorseData.getSeaHorseIdleAnimation());
        if (seaHorseData.getStepLeft() == 0) {
            seaHorseData.setState(SeaHorseState.EndAction);
            // System.out.println(seaHorseData.getState());
        }
        else {
            seaHorseData.setState(SeaHorseState.StartStep);
        }
    }

    public SeaHorse getSeaHorseData() {
        return seaHorseData;
    }

    public void setSeaHorseData(SeaHorse seaHorseData) {
        this.seaHorseData = seaHorseData;
    }

    public int[] getRelative() {
        int a[] = {seaHorseData.getRelativeX(), seaHorseData.getRelativeY()};
        return a;
    }

    public void setRelative(int x, int y) {
        seaHorseData.setRelativeX(x);
        seaHorseData.setRelativeY(y);
    }

    public SeaHorseView getSeaHorseView() {
        return seaHorseView;
    }
}
