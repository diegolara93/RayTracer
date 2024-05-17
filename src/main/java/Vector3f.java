public class Vector3f {
    float x, y, z;
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3f add(Vector3f other) {
        return new Vector3f(x + other.x, y + other.y, z + other.z);
    }
    public Vector3f multiply(Vector3f other) {
        return new Vector3f(x*other.x, y*other.y, z*other.z);
    }
    public Vector3f subtract(Vector3f other) {
        return new Vector3f(x-other.x, y-other.y,z-other.z);
    }
    public float dotProduct(Vector3f other) {
        return x*other.x + y*other.y + z*other.z;
    }
    public void printVector() {
        System.out.println(x + ", "+ y +", "+ z);
    }
}
