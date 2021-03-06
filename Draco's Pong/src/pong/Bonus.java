package pong;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class Bonus {
    public int b1x, b1y;
    public int b2x, b2y;
    public int b3x, b3y;
    public int width = 50;
    public int height = 50;
    public Random random;
    public boolean visible = false; // variable checking if the bonuses are visible
    private Pong pong;
    public boolean possible = true;  // variable checking if it is possible for the bonuses to show up
    public boolean wallVisible = false; // variable checking if the bonuswall is visible
    public Image imgBonus1;
    public Image imgBonus2;
    public Image imgBonus3;

    public Bonus(Pong pong) { // constuctor
        this.pong = pong;
        this.random = new Random();
        try {
            this.imgBonus1 = ImageIO.read(new File("src/img/bonus1.png"));  // initialize the img bonus1.png
        } catch (IOException ioe) {
            System.out.println("image: ball not found");
        }

        try {
            this.imgBonus2 = ImageIO.read(new File("src/img/bonus2.png"));  // initialize the img bonus2.png
        } catch (IOException ioe) {
            System.out.println("image: ball not found");
        }

        try {
            this.imgBonus3 = ImageIO.read(new File("src/img/bonus3.png"));  // // initialize the img bonus3.png
        } catch (IOException ioe) {
            System.out.println("image: ball not found");
        }
    }

    public void update(Ball ball, BonusBall bonusBall, Paddle paddle1, Paddle paddle2) {
        if (ball.amountOfHits % 20 == 6 && possible) {          // function for updating the visibility of the bonus
            visible = true;
            //this.spawn();
        }

        if (!visible) {
            this.spawn();
        }

        if (this.checkCollision(ball) == 1) { // spawns the bonusball if the bonusball bonus is being hit
            if (visible) {
                bonusBall.visibleBall = true;
                bonusBall.spawn();
            }

            visible = false;
        } else if (this.checkCollision(ball) == 2) {   // sets the paddles to be invisible
            if (visible) {                             // if bonus2 is being hit
                paddle1.pVisible = false;
                paddle2.pVisible = false;
                possible = false;
            }

            visible = false;
        } else if (this.checkCollision(ball) == 3) {   // spawns the bonuswall if the bonuswall bonus is being hit
            if (visible) {
                wallVisible = true;
            }

            visible = false;
        }
    }

    public void spawn() {                          // sets the positions of the bonuses (random)
        this.b1x = randomWithRange(100, 900);
        this.b1y = randomWithRange(100, 600);

        this.b2x = randomWithRange(100, 900);
        this.b2y = randomWithRange(100, 600);

        this.b3x = randomWithRange(100, 900);
        this.b3y = randomWithRange(100, 600);

        if (b2x == b1x) {b2x += 30;}
        if (b1x == b3x) {b1x += 30;}
        if (b3x == b2x) {b3x += 30;}
    }

    public int randomWithRange(int min, int max) {       // function for finding random numbers in range
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public int checkCollision(Ball ball) {                                                      // checks the collision
        if (!(this.b1x > (ball.x + ball.width) || (this.b1x + this.width) < ball.x) &&          // of the ball with the
                !(this.b1y > (ball.y + ball.height) || (this.b1y + this.height) < ball.y)) {    // bonuses
            return 1;
        } else if (!(this.b2x > (ball.x + ball.width) || (this.b2x + this.width) < ball.x) &&
                !(this.b2y > (ball.y + ball.height) || (this.b2y + this.height) < ball.y)) {
            return 2;
        } else if (!(this.b3x > (ball.x + ball.width) || (this.b3x + this.width) < ball.x) &&
                !(this.b3y > (ball.y + ball.height) || (this.b3y + this.height) < ball.y)) {
            return 3;
        } else {
            return 0;
        }
    }

    public void render(Graphics g) {                             // renders the bonuses
        g.drawImage(this.imgBonus1, this.b1x, this.b1y, null);
        g.drawImage(this.imgBonus2, this.b2x, this.b2y, null);
        g.drawImage(this.imgBonus3, this.b3x, this.b3y, null);
    }
}
