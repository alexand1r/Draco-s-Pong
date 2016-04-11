package pong;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import pong.BackgroundPanel;

import java.util.Random;

public class BonusBall {
    public int x;
    public int y;
    public int width = 25;
    public int height = 25;
    public int motionX;
    public int motionY;
    public int amount;
    public Random random;
    private Pong pong;
    public boolean visibleBall;
    public Image imgPaddle;

    public BonusBall(Pong pong) { //construktor s atribut obekt ot Pong
        this.pong = pong;
        this.random = new Random();
        this.spawn();
        try {
            this.imgPaddle = ImageIO.read(new File("src/pong/ball.png"));
        } catch (IOException ioe) {
            System.out.println("image: ball not found");
        }
        // funkciq na klasa za s1zdavane na top4eto v sredata na ekrana
    }                  // i izpra6tane v s1otvetna posoka

    public void update(Paddle paddle1, Paddle paddle2, Ball ball, Bonus bonus) {
        byte speed = 2;
        this.x += this.motionX * speed;
        this.y += this.motionY * speed;
        if (this.y + this.height - this.motionY > this.pong.height || this.y + this.motionY < 0) {
            if (this.motionY < 0) {
                this.y = 0;
                this.motionY = 1;          // proverqva dali top4eto
                if (this.motionY == 0) {                         // se e udarilo v gornata ili
                    this.motionY = 1;                           // dolanata stena
                }                                               // i ako da, da se otbl1skva
            } else {                                            // s1otvetno p1rvo za gornata i posle za dolnata stena
                this.motionY = -1;
                this.y = this.pong.height - this.height;
                if (this.motionY == 0) {
                    this.motionY = -1;
                }
            }
        }

        if (this.checkCollision(paddle1) == 1) {// a tezi 2te proverqvat za paddle-ite
            if (ball.amountOfHits < 20) {
                amount = ball.amountOfHits / 5;
            }
            this.motionX = 3 + amount;           // kato se uveli4ava skorostta na vseki 5 udara
            this.motionY = this.motionY + 1;         // i izpra6ta top4eto v random posoka ot -2 + ot 0 do 4
            if (this.motionY == 0) {                             // maha slu4aq s 0 za6toto da se izbegne to4no
                motionY = -random.nextInt((6 - 3) + 3);                               // prava horizontalna posoka t1i kato se
            }                                                   // polu4avat b1gove ako se udari v nqkoi ot r1bovete
            // na prozoreca
            ++ball.amountOfHits;
        } else if (this.checkCollision(paddle2) == 1) {
            if (ball.amountOfHits < 20) {
                amount = ball.amountOfHits / 5;
            }
            this.motionX = -3 - amount;
            this.motionY = this.motionY - 1;
            if (this.motionY == 0) {
                motionY = random.nextInt((6 - 3) + 3);
            }

            ++ball.amountOfHits;
        }

        if (this.checkCollision(paddle1) == 2) {                 // v slu4ai 4e tazi funkciq checkCollision v1rne 2
            ++paddle2.score;
            this.spawn();
            this.visibleBall = false;
            bonus.visible = false;
            bonus.possible = true;
            paddle1.pVisible = true;
            paddle2.pVisible = true;
            ball.spawn();
            pong.gameStatus = 4;                                       // respawnva top4eto po sredata
        } else if (this.checkCollision(paddle2) == 2) {
            ++paddle1.score;
            this.spawn();
            this.visibleBall = false;
            bonus.visible = false;
            bonus.possible = true;
            paddle1.pVisible = true;
            paddle2.pVisible = true;
            ball.spawn();
            pong.gameStatus = 4;
        }

    }

    public void spawn() {
        this.x = this.pong.width / 2 - this.width / 2;
        this.y = this.pong.height / 2 - this.height / 2;
        this.motionY = (Math.random() <= 0.5) ? 1 : -1;


        if (this.random.nextBoolean()) {
            this.motionX = 3;
        } else {
            this.motionX = -3;
        }

    }

    public int checkCollision(Paddle paddle) { // istinskata proverka dali top4eto udrq paddle-a koqto vr16ta 0, 1 ili 2
        return this.x < paddle.x + paddle.width &&  // oba4e nikoga nqma da v1rne 0 za6toto taka sa postaveni usloviqta
                this.x + this.width > paddle.x &&
                this.y < paddle.y + paddle.height &&
                this.y + this.height > paddle.y ? 1 : ((paddle.x <= this.x || paddle.paddleNumber != 1) &&
                (paddle.x >= this.x - this.width || paddle.paddleNumber != 2) ? 0 : 2);
    }

    public void render(Graphics g) { // iz4ertava top4eto
        g.setColor(Color.WHITE);
        //g.fillOval(this.x, this.y, this.width, this.height);
        g.drawImage(imgPaddle, this.x, this.y, null);
    }
}