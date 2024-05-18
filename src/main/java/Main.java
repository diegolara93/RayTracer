public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Matrix4f mat = new Matrix4f();
        Matrix4f mat2 = new Matrix4f();
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                mat.m[i][j] = (float) Math.random() * 10;
                mat2.m[i][j] = (float) Math.random() * 10;
            }
        }
        Matrix4f newMat = mat.multiply(mat2);
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                System.out.print(newMat.m[i][j] + " ");
//                System.out.print(mat.m[i][j] + " ");
            }
            System.out.println();
        }
    }
}