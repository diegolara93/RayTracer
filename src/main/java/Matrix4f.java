public class Matrix4f {
    public float[][] m;

    public Matrix4f() {
        m = new float[4][4];
    }

    public static Matrix4f identity() {
        Matrix4f mat = new Matrix4f();
        for(int i = 0; i < 4; i++) {
            mat.m[i][i] = 1.0f;
        }
        return mat;
    }
    public static Matrix4f perspective(float fov, float aspect, float near, float far) {
        Matrix4f mat = new Matrix4f();
        float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float range = near - far;

        mat.m[0][0] = 1.0f / (tanFOV * aspect);
        mat.m[1][1] = 1.0f / tanFOV;
        mat.m[2][2] = (-near - far) / range;
        mat.m[2][3] = 2 * far * near / range;
        mat.m[3][2] = 1.0f;
        mat.m[3][3] = 0.0f;

        return mat;
    }
    public static Matrix4f rotationY(float angle) {
        Matrix4f matrix = Matrix4f.identity();
        float rad = (float) Math.toRadians(angle);
        matrix.m[0][0] = (float) Math.cos(rad);
        matrix.m[0][2] = (float) Math.sin(rad);
        matrix.m[2][0] = (float) -Math.sin(rad);
        matrix.m[2][2] = (float) Math.cos(rad);
        return matrix;
    }

    public Matrix4f multiply(Matrix4f other) {
        Matrix4f mat = new Matrix4f();
        float sum = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                sum += this.m[row][col] * other.m[row][col];
                if (col == 3) {
                    mat.m[row][col] = sum;
                }
            }
        }
        return mat;
    }
}
