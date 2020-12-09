package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DRX {
    double ro;
    double A = 86710969050178.5;
    double B = 9.41268203527779;
    float t = (float) 0.001;
    Neighbourhood neighbourhood;
    List<List<Point> > listOfListOfPoints = new ArrayList<>();

    public DRX() {
        neighbourhood = new Neighbourhood();
    }

    public double calculateRo(float t) {
        ro = A / B + (1 - (A / B)) * Math.pow(Math.E, ((-B) * t));

        return ro;
    }

    public double calculateDeltaRo(float t1, float t2) {
        double ro1 = calculateRo(t1);
        double ro2 = calculateRo(t2);
        double deltaRo = ro2 - ro1;

        return deltaRo;
    }

    public double packOfDislocation(double deltaRo, int numberOnTheEdge, int numberInside, int size) {
        double cellsOnTheEdge = 0.7 * (deltaRo / (size * size));
        double cellsInside = 0.3 * (deltaRo / (size * size));

        double numberOfLeftDislocations = deltaRo - (cellsOnTheEdge * numberOnTheEdge
                                                    + cellsInside * numberInside)/(size*size);

        return numberOfLeftDislocations;
    }


//    public int allocationOfDislocation(int size, Nucleation nucleation, Nucleation newNucleationGrid,
//                                        int checkBoxSelected, List<Point> pointsOnTheEdges,
//                                        List<Point> pointsInside,
//                                        WritableImage snap, GraphicsContext graphicsContext) {
//
//        List<Point> newPointEdges = new ArrayList<>();
//        List<Point> newPointInside = new ArrayList<>();
//
//        if(t == 0.01){
//            return 0;
//        }
//
//    //    System.out.println(t);
//        float t1 = t;
//        float t2 = (float) (t + 0.001);
//        double deltaRo = calculateDeltaRo(t1, t2);
//
//        for(Point item : pointsOnTheEdges) {
//            Point newPoint = new Point(item.getX(), item.getY(), item.getState(),
//                    item.getDislocation() + (0.7 * (deltaRo / (size * size))),
//                    item.getRecrystallization());
//
//            newPointEdges.add(newPoint);
//        }
//
//        for(Point item : pointsInside) {
//            Point newPoint = new Point(item.getX(), item.getY(), item.getState(),
//                    item.getDislocation() + (0.3 * (deltaRo / (size * size))),
//                    item.getRecrystallization());
//
//            newPointInside.add(newPoint);
//        }
//
//        listOfListOfPoints.add(newPointEdges);
//        listOfListOfPoints.add(newPointInside);
//
//        double numberOfLeftDislocations = packOfDislocation(deltaRo, pointsOnTheEdges.size(),
//                pointsInside.size(), size);
//
//        Random random = new Random();
//        List<Integer> listToRandom = new ArrayList<>();
//        listToRandom.add(1);
//        listToRandom.add(1);
//        listToRandom.add(1);
//        listToRandom.add(1);
//        listToRandom.add(2);
//
//        while(numberOfLeftDislocations > 0.0) {
//
//            BigDecimal chooseSmallPack = new BigDecimal(numberOfLeftDislocations * 0.08);
//            double chooseSmallPackDBL = chooseSmallPack.doubleValue();
//            if(chooseSmallPackDBL == 0.0) {
//                chooseSmallPackDBL = numberOfLeftDislocations;
//            }
//
//            int chooseList = listToRandom.get(random.nextInt(listToRandom.size()));
//
//            if (chooseList == 1) {
//                Point randomPointEdge = newPointEdges.get(random.nextInt(pointsOnTheEdges.size()));
//                randomPointEdge.setDislocation(randomPointEdge.getDislocation() + chooseSmallPackDBL);
//                randomPointEdge.setRecrystallization(0);
//            } else {
//                Point randomPointInside = newPointInside.get(random.nextInt(pointsInside.size()));
//                randomPointInside.setDislocation(randomPointInside.getDislocation() + chooseSmallPackDBL);
//                randomPointInside.setRecrystallization(0);
//            }
//            numberOfLeftDislocations -= chooseSmallPackDBL;
//        }
//
//
//        double criticalValue = 4215840142323.42 / (size * size);
//
//        List<Point> newListPointEdges = new ArrayList<>();
//        List<Point> newListPointInside = new ArrayList<>();
//
//
//
//
//
//        for(Point item : newPointInside){
//            double dislocation = item.getDislocation();
//            int rec = item.getRecrystallization();
//
//            int res = checkNeighbours(item.getX(), item.getY(), neighbourhood.neumannCells,
//                    nucleation, checkBoxSelected,
//                    size, dislocation, rec);
//
//            Point newPointInsideGeneration;
//           // System.out.println(res);
//            if(res == 1){
//           //     System.out.println("regula dwaaaaaaaaaaa");
//                newNucleationGrid.makeCellAlive(item.getX(), item.getY(), 2000000 );
//                graphicsContext.setFill(Color.WHITE);
//                graphicsContext.fillRect(item.getX(), item.getY(), 1,1);
//
//                newPointInsideGeneration = new Point(item.getX(), item.getY(),
//                        2000000, 0, 1);
//
//            }
//            else{
//                newPointInsideGeneration = item;
//            }
//            newListPointInside.add(newPointInsideGeneration);
//        }
//        for(Point item : newPointEdges){
//            Point newPointEdgeGeneration;
//            if(item.getDislocation() > criticalValue){
//                newNucleationGrid.makeCellAlive(item.getX(), item.getY(), 2000000 );
//                graphicsContext.setFill(Color.WHITE);
//                graphicsContext.fillRect(item.getX(), item.getY(), 1,1);
//
//                newPointEdgeGeneration = new Point(item.getX(), item.getY(),
//                        2000000, 0, 1);
//
//            }
//            else{
//                newPointEdgeGeneration = item;
//            }
//
//            newListPointEdges.add(newPointEdgeGeneration);
//        }
//
//
//        listOfListOfPoints.add(newListPointEdges);
//        listOfListOfPoints.add(newListPointInside);
//
//        t += 0.001;
//        return 1;
//    }
//
//
//
//    public int checkNeighbours(int x, int y, int[][] neighbourCells, Nucleation nucleation, int checkBoxSelected,
//                               int size, double dislocation, int rec) {
//        int j = 0;
//        int state = -1;
//        int recystalRes = 0;
//        int dislocRes = 0;
//        int stateOfMyCell = nucleation.getStateAbsorbingBC(x, y);
//
//        Point myPoint = new Point(x, y, stateOfMyCell, dislocation, rec);
//
//       // System.out.println("size: " + listOfListOfPoints.size());
//        for (int i = 0; i < neighbourCells.length; i++) {
//            int iTemp = x + neighbourCells[i][j];
//            int jTemp = y + neighbourCells[i][j + 1];
//
//            state = checkStateBC(iTemp, jTemp, nucleation, checkBoxSelected, size);
//
//          //  Point newPoint = new Point(iTemp, jTemp, 20000, 0, 1);
//
//            recystalRes += checKExistingRecrysNeighbour(iTemp, jTemp, state);
//          //  System.out.println("recystalRes: " + recystalRes);
//
//            dislocRes += checkDislocation(myPoint, iTemp, jTemp, state);
//
//
//        }
//       // System.out.println("dis: " + dislocRes);
//        if(recystalRes != 0 && dislocRes != 0){
//            return 1;
//        }
//        else {
//            return 0;
//        }
//    }
//
//    public int checkDislocation(Point myPoint, int iTemp, int jTemp, int state) {
//        int counterEdge = 0;
//        int counterInside = 0;
////
////        if(listOfListOfPoints.size() > 1) {
////
////            int i = listOfListOfPoints.size() - 4;
////
////        //    System.out.println("Rozmiar podlisty: " + listOfListOfPoints.get(i).size());
////           // for(int i = listOfListOfPoints.size() - 4; i < listOfListOfPoints.size() - 2; i++){
////                for(int j = 0; j < listOfListOfPoints.get(i).size(); j++) {
////                    Point temp = listOfListOfPoints.get(i).get(j);
////                    if(temp.getX() == iTemp &&
////                        temp.getY() == jTemp &&
////                            temp.getState() == state &&
////                            (temp.getDislocation() < myPoint.getDislocation())) {
////
////                        counterEdge++;
////                    }
////                }
////        //    }//
////            i = listOfListOfPoints.size() - 3;
//           // System.out.println("Rozmiar podlisty: " + listOfListOfPoints.get(i).size());
//            for(int i = listOfListOfPoints.size() - 4; i < listOfListOfPoints.size() - 2; i++){
//                for(int j = 0; j < listOfListOfPoints.get(i).size(); j++) {
//                    Point temp = listOfListOfPoints.get(i).get(j);
////
////                    System.out.println(temp.getX() +" "+ iTemp +
//////                            "\n" +temp.getY()+" "+ jTemp +
////                            "\n" +temp.getState()+" "+ state);
//////                            "\n" +temp.getDislocation()+" "+ myPoint.getDislocation() +
//////                            "\n" +temp.getRecrystallization() + " " + 0);
//
//                    if(temp.getX() == iTemp &&
//                            temp.getY() == jTemp &&
////                            temp.getState() == state &&
//                            (temp.getDislocation() < myPoint.getDislocation()) &&
//                            temp.getRecrystallization() == 0) {
//                       // System.out.println("w sroooooooooooodkuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
//
//                        return 1;
//                     //   System.out.println(counterInside);
//                    }
//                }
//            }
//    //    }
//
//      //  int res = counterInside;
//
//
//
//        return 0;
//    }
//
//
//
//    public int checKExistingRecrysNeighbour(int iTemp, int jTemp, int state) {
//       // if(listOfListOfPoints.size() > 1) {
//           // System.out.println("20");
////            if (listOfListOfPoints.get(listOfListOfPoints.size() - 3)
////                    .contains(newPoint)
////                    || listOfListOfPoints.get(listOfListOfPoints.size() - 2)
////                    .contains(newPoint)){
////                return 1;
////
////            }
//         //   System.out.println(iTemp +" "+ jTemp);
//
////            //for(int i = listOfListOfPoints.size() - 4; i < listOfListOfPoints.size() - 3; i++){
////
////                int i = listOfListOfPoints.size() - 4;
////          //  System.out.println("Rozmiar podlisty: " + listOfListOfPoints.get(i).size());
////                for(int j = 0; j < listOfListOfPoints.get(i).size(); j++) {
////                    Point temp = listOfListOfPoints.get(i).get(j);
////
////                    System.out.println("");
////
////                    System.out.println(temp.getX() +" "+ iTemp +
////                            "\n" +temp.getY()+" "+ jTemp +
////                            "\n" +temp.getState()+" "+ 2000000 +
////                            "\n" +temp.getDislocation()+" "+ 0.0 +
////                            "\n" +temp.getRecrystallization() + " " + 1);
//////
//////                    System.out.println(iTemp +" "+ jTemp+" "+ 2000000 +" "+
//////                            0.0+" "+ 1);
////                    System.out.println("");
////
//////                    if(temp.getX() == iTemp &&
//////                            temp.getY() == jTemp &&
////////                            temp.getState() == 2000000 &&
////////                            temp.getDislocation() == 0.0 &&
//////                            (temp.getRecrystallization() == 1)) {
//////                        System.out.println("Weszlam do petli edge");
//////
//////                        return 1;
//////                    }
////
////                    if(temp.getX() == iTemp &&
////                            temp.getY() == jTemp){
////                        System.out.println("Znalazloooo");
////                    }
////                }
//           // }
////                i = listOfListOfPoints.size() - 3;
//         //   System.out.println("Rozmiar podlisty: " + listOfListOfPoints.get(i).size());
//            for(int i = listOfListOfPoints.size() - 4; i < listOfListOfPoints.size() - 2; i++){
//                for(int j = 0; j < listOfListOfPoints.get(i).size(); j++) {
////                    System.out.println("");
////                    System.out.println("");
//                    Point temp = listOfListOfPoints.get(i).get(j);
////                    System.out.println(temp.getX() +" "+ iTemp +
////                            "\n" +temp.getY()+" "+ jTemp +
////                            "\n" +temp.getState()+" "+ 2000000 +
////                            "\n" +temp.getDislocation()+" "+ 0.0 +
////                            "\n" +temp.getRecrystallization() + " " + 1);
////
//////                    System.out.println(iTemp +" "+ jTemp+" "+ 2000000 +" "+
//////                            0.0+" "+ 1);
////                    System.out.println("");
//                    if(temp.getX() == iTemp &&
//                            temp.getY() == jTemp &&
//                            temp.getState() == 2000000 &&
//                            temp.getDislocation() == 0.0 &&
//                            (temp.getRecrystallization() == 1)) {
//                 //       System.out.println("insideeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//                        return 1;
//                    }
////                    if(temp.getX() == iTemp &&
////                            temp.getY() == jTemp){
////                        System.out.println("Znalazloooo wewnatrz");
////                    }
//                   }
//
//
//
//                }
//           // }
//       // }
//        return 0;
//    }
//
//    public int checkStateBC(int x, int y, Nucleation nucleation, int checkBoxSelected, int size) {
//        int state = -1;
//
//        if(checkBoxSelected == 1){
//            state = nucleation.getStatePeriodicBC(x, y, size);
//
//        } else if(checkBoxSelected == 0){
//            state = nucleation.getStateAbsorbingBC(x, y);
//        }
//        return state;
//    }

    public int allocationOfDislocation(int size, Nucleation nucleation, Nucleation newNucleationGrid,
                                       int checkBoxSelected, List<Point> pointsOnTheEdges,
                                       List<Point> pointsInside,
                                       WritableImage snap, GraphicsContext graphicsContext) {

        List<Point> newPointEdges = new ArrayList<>();
        List<Point> newPointInside = new ArrayList<>();
//
//        if(t == 10){
//            return 0;
//        }

        float t1 = t;
        float t2 = (float) (t + 0.001);
        double deltaRo = calculateDeltaRo(t1, t2);

        for(Point item : pointsOnTheEdges) {
            if(item.getRecrystallization() == 1){
                pointsOnTheEdges.remove(item);
            }
            else {
                Point newPoint = new Point(item.getX(), item.getY(), item.getState(),
                        item.getDislocation() + (0.7 * (deltaRo / (size * size))),
                        item.getRecrystallization());

                newPointEdges.add(newPoint);
            }
        }

        for(Point item : pointsInside) {
            if(item.getRecrystallization() == 1) {
                pointsInside.remove(item);
            }
            else {
                Point newPoint = new Point(item.getX(), item.getY(), item.getState(),
                        item.getDislocation() + (0.3 * (deltaRo / (size * size))),
                        item.getRecrystallization());

                newPointInside.add(newPoint);
            }
        }

        listOfListOfPoints.add(newPointEdges);
        listOfListOfPoints.add(newPointInside);

        double numberOfLeftDislocations = packOfDislocation(deltaRo, pointsOnTheEdges.size(),
                pointsInside.size(), size);

        Random random = new Random();
        List<Integer> listToRandom = new ArrayList<>();
        listToRandom.add(1);
        listToRandom.add(1);
        listToRandom.add(1);
        listToRandom.add(1);
        listToRandom.add(2);

        while(numberOfLeftDislocations > 0.0) {

            BigDecimal chooseSmallPack = new BigDecimal(numberOfLeftDislocations * 0.15);
            double chooseSmallPackDBL = chooseSmallPack.doubleValue();
            if(chooseSmallPackDBL == 0.0) {
                chooseSmallPackDBL = numberOfLeftDislocations;
            }

            int chooseList = listToRandom.get(random.nextInt(listToRandom.size()));
            if(newPointEdges.size() < 2){
                chooseList = 2;
            }

            if (chooseList == 1) {
                Point randomPointEdge = newPointEdges.get(random.nextInt(pointsOnTheEdges.size()));
                randomPointEdge.setDislocation(randomPointEdge.getDislocation() + chooseSmallPackDBL);
                randomPointEdge.setRecrystallization(0);
            } else {
                Point randomPointInside = newPointInside.get(random.nextInt(pointsInside.size()));
                randomPointInside.setDislocation(randomPointInside.getDislocation() + chooseSmallPackDBL);
                randomPointInside.setRecrystallization(0);
            }
            numberOfLeftDislocations -= chooseSmallPackDBL;
        }


        double criticalValue = 4215840142323.42 / (size * size);

        List<Point> recrylDone = new ArrayList<>();
        List<Point> notDoneRec = new ArrayList<>();





        for(Point item : newPointInside){
            double dislocation = item.getDislocation();
            int rec = item.getRecrystallization();

            int res = checkNeighbours(item.getX(), item.getY(), neighbourhood.neumannCells,
                    nucleation, checkBoxSelected,
                    size, dislocation, rec);

            Point newPointInsideGeneration;
            // System.out.println(res);
            if(res == 1){
                //     System.out.println("regula dwaaaaaaaaaaa");
                newNucleationGrid.makeCellAlive(item.getX(), item.getY(), 2000000 );
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillRect(item.getX(), item.getY(), 1,1);

                newPointInsideGeneration = new Point(item.getX(), item.getY(),
                        2000000, 0, 1);
                recrylDone.add(newPointInsideGeneration);
            }
            else{
                newPointInsideGeneration = item;
                notDoneRec.add(item);
            }

        }

        for(Point item : newPointEdges){
            Point newPointEdgeGeneration;
            if(item.getDislocation() > criticalValue){
                newNucleationGrid.makeCellAlive(item.getX(), item.getY(), 2000000 );
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillRect(item.getX(), item.getY(), 1,1);

                newPointEdgeGeneration = new Point(item.getX(), item.getY(),
                        2000000, 0, 1);
                recrylDone.add(newPointEdgeGeneration);

            }
            else{
                newPointEdgeGeneration = item;
                notDoneRec.add(item);
            }
        }


        listOfListOfPoints.add(recrylDone);
        listOfListOfPoints.add(notDoneRec);

        t += 0.001;
        return 1;
    }



    public int checkNeighbours(int x, int y, int[][] neighbourCells, Nucleation nucleation, int checkBoxSelected,
                               int size, double dislocation, int rec) {
        int j = 0;
        int state = -1;
        int recystalRes = 0;
        int dislocRes = 0;
        int stateOfMyCell = nucleation.getStateAbsorbingBC(x, y);

        Point myPoint = new Point(x, y, stateOfMyCell, dislocation, rec);

        // System.out.println("size: " + listOfListOfPoints.size());
        for (int i = 0; i < neighbourCells.length; i++) {
            int iTemp = x + neighbourCells[i][j];
            int jTemp = y + neighbourCells[i][j + 1];

            state = checkStateBC(iTemp, jTemp, nucleation, checkBoxSelected, size);

            //  Point newPoint = new Point(iTemp, jTemp, 20000, 0, 1);

            recystalRes += checKExistingRecrysNeighbour(iTemp, jTemp, state);
            //  System.out.println("recystalRes: " + recystalRes);

            dislocRes += checkDislocation(myPoint, iTemp, jTemp, state);


        }
        // System.out.println("dis: " + dislocRes);
        if(recystalRes > 0 && dislocRes == 4){
            return 1;
        }
        else {
            return 0;
        }
    }

    public int checkDislocation(Point myPoint, int iTemp, int jTemp, int state) {
        int counterEdge = 0;
        int counterInside = 0;

        for(int i = listOfListOfPoints.size() - 4; i < listOfListOfPoints.size() - 2; i++){
            for(int j = 0; j < listOfListOfPoints.get(i).size(); j++) {
                Point temp = listOfListOfPoints.get(i).get(j);


                if(temp.getX() == iTemp &&
                        temp.getY() == jTemp &&
                            temp.getState() == state &&
                        (temp.getDislocation() <= myPoint.getDislocation()) &&
                        temp.getRecrystallization() == 0) {

                    return 1;
                }
            }
        }

        return 0;
    }



    public int checKExistingRecrysNeighbour(int iTemp, int jTemp, int state) {

        for(int i = listOfListOfPoints.size() - 4; i < listOfListOfPoints.size() - 2; i++){
           // for(int j = 0; j < listOfListOfPoints.get(i).size(); j++) {

              //  Point temp = listOfListOfPoints.get(i).get(j);
                Point temp = new Point(iTemp, jTemp, 2000000, 0.0, 1);
                if(listOfListOfPoints.get(i).contains(temp)){
                    return 1;
                }

//                if(temp.getX() == iTemp &&
//                        temp.getY() == jTemp &&
//                        temp.getState() == 2000000 &&
//                        temp.getDislocation() == 0.0 &&
//                        (temp.getRecrystallization() == 1)) {
//
//                    return 1;
//                }

            //}



        }

        return 0;
    }

    public int checkStateBC(int x, int y, Nucleation nucleation, int checkBoxSelected, int size) {
        int state = -1;

        if(checkBoxSelected == 1){
            state = nucleation.getStatePeriodicBC(x, y, size);

        } else if(checkBoxSelected == 0){
            state = nucleation.getStateAbsorbingBC(x, y);
        }
        return state;
    }

}
