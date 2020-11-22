package sample;

public class Neighbourhood {
    int x;
    int y;




    public void neumannNeigbour(int x, int y, Nucleation nucleation, Nucleation newNucleationGrid){

        int[][] neumannCells = {{-1, 0},
                                {0, -1},
                                {1, 0},
                                {0, 1}};

        int j = 0;
        int state;

        for(int i = 0; i < 4; i++){
            int iTemp = x + neumannCells[i][j];
            int jTemp = y + neumannCells[i][j + 1];

            state = nucleation.getStateAbsorbingBC(iTemp, jTemp);

            if(state != 0){
               // newNucleation
            }
        }

    }


}
