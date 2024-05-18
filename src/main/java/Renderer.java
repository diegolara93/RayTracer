import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer extends JPanel {
    private BufferedImage canvas;
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Vector3f[] vertices;

    public Renderer(int width, int height) {
        this.canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.projectionMatrix = Matrix4f.perspective(90, (float) width / height, 0.1f, 1000.0f);
        this.viewMatrix = Matrix4f.identity(); // Initialize view matrix as identity

        // Initialize vertices of a cube
        this.vertices = new Vector3f[]{
                new Vector3f(-1, -1, -1),
                new Vector3f(1, -1, -1),
                new Vector3f(1, 1, -1),
                new Vector3f(-1, 1, -1),
                new Vector3f(-1, -1, 1),
                new Vector3f(1, -1, 1),
                new Vector3f(1, 1, 1),
                new Vector3f(-1, 1, 1)
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderScene();
        g.drawImage(canvas, 0, 0, null);
    }

    private void renderScene() {
        // Clear the canvas
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Apply transformations and project vertices
        Matrix4f modelMatrix = Matrix4f.rotationY(45); // Example transformation
        Matrix4f mvpMatrix = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);

        Vector3f[] transformedVertices = new Vector3f[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            transformedVertices[i] = transform(vertices[i], mvpMatrix);
        }

        // Draw the cube
        renderCube(g2d, transformedVertices);

        g2d.dispose();
    }

    private Vector3f transform(Vector3f vertex, Matrix4f matrix) {
        float x = vertex.x * matrix.m[0][0] + vertex.y * matrix.m[1][0] + vertex.z * matrix.m[2][0] + matrix.m[3][0];
        float y = vertex.x * matrix.m[0][1] + vertex.y * matrix.m[1][1] + vertex.z * matrix.m[2][1] + matrix.m[3][1];
        float z = vertex.x * matrix.m[0][2] + vertex.y * matrix.m[1][2] + vertex.z * matrix.m[2][2] + matrix.m[3][2];
        float w = vertex.x * matrix.m[0][3] + vertex.y * matrix.m[1][3] + vertex.z * matrix.m[2][3] + matrix.m[3][3];

        return new Vector3f(x / w, y / w, z / w);
    }

    private void renderCube(Graphics2D g2d, Vector3f[] vertices) {
        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0},
                {4, 5}, {5, 6}, {6, 7}, {7, 4},
                {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };

        for (int[] edge : edges) {
            Vector3f start = vertices[edge[0]];
            Vector3f end = vertices[edge[1]];
            drawLine(g2d, start, end);
        }
    }

    private void drawLine(Graphics2D g2d, Vector3f start, Vector3f end) {
        int x1 = (int) ((start.x + 1) * canvas.getWidth() / 2);
        int y1 = (int) ((start.y + 1) * canvas.getHeight() / 2);
        int x2 = (int) ((end.x + 1) * canvas.getWidth() / 2);
        int y2 = (int) ((end.y + 1) * canvas.getHeight() / 2);
        g2d.setColor(Color.WHITE);
        g2d.drawLine(x1, y1, x2, y2);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("3D Renderer");
        Renderer renderer = new Renderer(800, 600);
        frame.add(renderer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Main rendering loop
        Timer timer = new Timer(16, e -> {
            renderer.repaint();
        });
        timer.start();
    }
}
