import java.awt.Color;
import java.util.Random;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.FontStyle;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import javalib.worldimages.WorldImage;
import tester.Tester;

//represents a list of objects
interface IList<T> {
  //folds a list when given a folding function and a base
  <U> U foldr(IFunc<T, U, U> func, U base);

  //transforms a list by applying the given function to the list elements
  <U> IList<U> map(IFunc2<T, U> f);

  //filters a list by applying given boolean function and a value to compare with
  <U> IList<T> filter(IFunc3<T, U> f, U comp);

  //returns true if element passes test
  <U> boolean ormap(IFunc4<T, U> f, U comp);

  //returns true if element passes test, uses 2 parameters for test, instead of 1 
  <U> boolean ormap2(IFunc5<T, U, U> f, U comp1, U comp2);
}

//represents an empty list
class MtList<T> implements IList<T> {
  
  /*
  TEMPLATE
  FIELDS:
  this.func ... Predicate<T>

  METHODS
  U foldr(IFunc<T, U, U> func, U base)
  IList<U> map(IFunc2<T, U> f)
  IList<T> filter(IFunc3<T, U> f, U comp)
  boolean ormap(IFunc4<T, U> f, U comp)
  boolean ormap2(IFunc5<T, U, U> f, U comp1, U comp2)
  */
  
  //folds a list when given a folding function and a base
  public <U> U foldr(IFunc<T, U, U> func, U base) {
    return base;
  }

  //transforms a list by applying the given function to the list elements
  public <U> IList<U> map(IFunc2<T, U> f) { 
    return new MtList<U>(); 
  }

  //filters a list by applying given boolean function
  public <U> IList<T> filter(IFunc3<T, U> f, U comp) { 
    return new MtList<T>(); 
  }

  //returns true if element passes test, uses a parameter for comparison
  public <U> boolean ormap(IFunc4<T, U> f, U comp) { 
    return false;
  }

  //returns true if element passes test, uses 2 parameters for comparison 
  public <U> boolean ormap2(IFunc5<T, U, U> f, U comp1, U comp2) { 
    return false;
  }


}

//represents a non-empty list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /*
  TEMPLATE
  FIELDS:
  this.first ... T
  this.rest ... IList<T>

  METHODS
  U foldr(IFunc<T, U, U> func, U base)
  IList<U> map(IFunc2<T, U> f)
  IList<T> filter(IFunc3<T, U> f, U comp)
  boolean ormap(IFunc4<T, U> f, U comp)
  boolean ormap2(IFunc5<T, U, U> f, U comp1, U comp2)
  
  METHODS for fields
  this.first:
  apply()
  
  this.rest:
  
  U foldr(IFunc<T, U, U> func, U base)
  IList<U> map(IFunc2<T, U> f)
  IList<T> filter(IFunc3<T, U> f, U comp)
  boolean ormap(IFunc4<T, U> f, U comp)
  boolean ormap2(IFunc5<T, U, U> f, U comp1, U comp2)
   */
  
  //folds a list when given a folding function and a base
  public <U> U foldr(IFunc<T, U, U> func, U base) {
    
    /*
    PARAMETERS
    ...func ...           -- IFunc<T, U, U>
    ... base ...       -- U

     */
    
    return func.apply(this.first,
        this.rest.foldr(func, base));
  }

  //transforms a list by applying the given function to the list elements
  public <U> IList<U> map(IFunc2<T, U> f) {
    
    /*
    PARAMETERS
    ...f ...           -- IFunc2<T, U>
     */
    
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }

  //filters a list by applying given boolean function
  public <U> IList<T> filter(IFunc3<T, U> f, U comp) {
    
    /*
    PARAMETERS
    ...f ...           -- IFunc3<T, U
    ... comp ...       -- U

     */
    
    if (f.apply(this.first, comp)) {
      return this.rest.filter(f, comp);
    }
    else {
      return new ConsList<T>(this.first, this.rest.filter(f, comp));
    }
  }

  //returns true if element passes test (comp used for test)
  public <U> boolean ormap(IFunc4<T, U> f, U comp) {
    
    /*
    PARAMETERS
    ...f...           -- IFunc4<T, U
    ... comp ...       -- U
     */
    
    return f.apply(this.first, comp) || this.rest.ormap(f, comp);
  }

  //returns true if element passes test, uses 2 parameters for test, instead of 1 
  public <U> boolean ormap2(IFunc5<T, U, U> f, U comp1, U comp2) {
    
    /*
    PARAMETERS
    ...f...           -- IFunc5<T, U, U>
    ... comp1 ...       -- U
    ... comp2 ...       -- U
     */
    
    return f.apply(this.first, comp1, comp2) || this.rest.ormap2(f, comp1, comp2);
  }


}

//Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc<A1, A2, R> {
  R apply(A1 arg1, A2 arg2);
}

//a class where apply function draws all invaders in a list of invaders
class DrawInv implements IFunc<Invader, WorldScene, WorldScene> {
  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Invader i, WorldScene scene)  ...       -- WorldScene
   */

  //draws all invaders in a list of invaders
  public WorldScene apply(Invader i, WorldScene scene) {
    /*
    TEMPLATE
    PARAMETERS
    ... i ...           -- Invader
    ... scene ...       -- WorldScene

    METHODS FOR PARAMETERS
    ... this.i.move() ...       -- Spaceship
    ... this.i.turn() ...       -- Spaceship
    ... this.i.draw() ...       -- WorldScene
    ... this.i.maker() ...      -- Bullet
this.i.generate(int a, int b, int xGap, int yGap, int startingX, IList<Bullet> lob) --IList<Bullet>
     */
    return i.draw(scene);
  }
}

//a class where the apply function draws all bullets in a list of bullets
class DrawBullets implements IFunc<Bullet, WorldScene, WorldScene> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Bullet b, WorldScene scene) ...       -- WorldScene
   */

  //draws all bullets in a list of bullets
  public WorldScene apply(Bullet b, WorldScene scene) {
    /*
    TEMPLATE
    PARAMETERS
    ... b ...           -- Bullet
    ... scene ...       -- WorldScene

    METHODS FOR PARAMETERS
    ... this.b.move() ...       -- Bullet
    ... this.b.draw() ...       -- WorldScene
     */
    return b.draw(scene);
  }
}

//a class where the apply function counts the amount of bullets fired by the ship 
class CountSB implements IFunc<Bullet, Integer, Integer> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Bullet b, Integer count) ...       -- Integer
   */

  //counts the amount of bullets fired by the ship 
  public Integer apply(Bullet b, Integer count) {
    /*
    TEMPLATE
    PARAMETERS
    ... b ...           -- Bullet
    ... count ...       -- Integer

    METHODS FOR PARAMETERS
    ... this.b.move() ...       -- Bullet
    ... this.b.draw() ...       -- WorldScene
     */

    if (b.isShips) {
      return 1 + count; 
    }
    else {
      return count;
    }
  }
}

//a class where the apply function counts the amount of bullets fired by invaders 
class CountIB implements IFunc<Bullet, Integer, Integer> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Bullet b, Integer count) ...       -- Integer
   */

  //counts the amount of bullets fired by invaders 
  public Integer apply(Bullet b, Integer count) {
    /*
    TEMPLATE
    PARAMETERS
    ... b ...           -- Bullet
    ... count ...       -- Integer

    METHODS FOR PARAMETERS
    ... this.b.move() ...       -- Bullet
    ... this.b.draw(WorldScene scene) ...       -- WorldScene
     */
    if (!b.isShips) {
      return 1 + count; 
    }
    else {
      return count;
    }
  }
}

//a class where the apply function counts the number of invaders
class CountInv implements IFunc<Invader, Integer, Integer> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Invader i, Integer count) ...       -- Integer
   */

  //counts the number of invaders
  public Integer apply(Invader i, Integer count) {
    /*
    TEMPLATE
    PARAMETERS
    ... i ...           -- Invader
    ... count ...       -- Integer

    METHODS FOR PARAMETERS
    ... this.i.draw(WorldScene scene) ...       -- WorldScene
     */
    return 1 + count;
  }
}


//a class where the apply function deletes bullets outside of the screen
class FilterFrame implements IFunc<Bullet, IList<Bullet>, IList<Bullet>> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Bullet b, IList<Bullet> lob) ...       -- IList<Bullet>
   */

  int sceneWidth = 1000; 
  int sceneHeight = 1000;

  //deletes bullets outside of the screen
  public IList<Bullet> apply(Bullet b, IList<Bullet> lob) {

    /*
    TEMPLATE
    PARAMETERS
    ... b ...           -- Bullet
    ... lob ...         -- IList<Bullet>

    METHODS FOR PARAMETERS
    ... this.b.draw(WorldScene scene) ...       -- WorldScene
    foldr:
     ... new DrawBullets(), Bullet b, WorldScene scene ...             -- boolean
     ... new CountSB(), Bullet b, Integer count ...             -- int
     ... new CountIB(), Bullet b, Integer count ...             -- int
     ... new FilterFrame(), Bullet b, IList<Bullet> lob...             -- IList<Bullet>
    map:
     ... new MoveBullet(), Bullet b...                        -- IList<Bullet>
    filter:
     ... new BulletDie() Bullet b, IList<Invader> loi ...             -- IList<Bullet>
    ormap:
     ... new CheckLose(), Bullet b, Spaceship s ...             -- boolean
     ... new CheckHit().apply(Bullet b, Invader i1) ...             -- boolean

     */

    if (b.x <= sceneWidth && b.x >= 0 && b.y <= sceneHeight && b.y >= 0) {
      return new ConsList<Bullet>(b, lob); 
    }
    else {
      return lob;
    }
  }
}

//Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc2<A1, A2> {
  A2 apply(A1 arg1);
}

//a class where the apply function moves bullets
class MoveBullet implements IFunc2<Bullet, Bullet> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Bullet b) ...       -- Bullet
   */

  //moves bullets
  public Bullet apply(Bullet b) {

    /*
    TEMPLATE
    PARAMETERS
    ... b ...           -- Bullet

    METHODS FOR PARAMETERS
    ... this.b.draw(WorldScene scene) ...       -- WorldScene
     */

    return b.move();
  }
}



//Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc3<A1, A2> {
  boolean apply(A1 arg1, A2 arg2);
}

//class where the apply function deletes or keeps invader based on bullet coordinates
class InvaderDie implements IFunc3<Invader, IList<Bullet>> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Invader i1, IList<Bullet> lob) ...       -- boolean
   */

  //deletes or keeps invader based on bullet coordinates
  public boolean apply(Invader i1, IList<Bullet> lob) {

    /*
    TEMPLATE
    PARAMETERS
    ... i1 ...           -- Invader
    ... lob ...          -- IList<Bullet>

    METHODS FOR PARAMETERS
    ... this.b.draw(WorldScene scene) ...       -- WorldScene
    foldr:
     ... new DrawBullets(), Bullet b, WorldScene scene ...             -- boolean
     ... new CountSB(), Bullet b, Integer count ...             -- int
     ... new CountIB(), Bullet b, Integer count ...             -- int
     ... new FilterFrame(), Bullet b, IList<Bullet> lob...             -- IList<Bullet>
    map:
     ... new MoveBullet(), Bullet b...                        -- IList<Bullet>
    filter:
     ... new BulletDie() Bullet b, IList<Invader> loi ...             -- IList<Bullet>
    ormap:
     ... new CheckLose(), Bullet b, Spaceship s ...             -- boolean
     ... new CheckHit().apply(Bullet b, Invader i1) ...             -- boolean

     */

    return lob.ormap(new CheckHits(), i1);
  }
}

//a class where the apply function deletes or keeps bullets based on collisions
class BulletDie implements IFunc3<Bullet, IList<Invader>> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Bullet b, IList<Invader> loi) ...       -- boolean
   */

  //deletes or keeps bullets based on collisions
  public boolean apply(Bullet b, IList<Invader> loi) {

    /*
    TEMPLATE
    PARAMETERS
    ... b ...            -- Bullet
    ... loi ...          -- IList<Invader>

    METHODS FOR PARAMETERS
    ... this.b.draw(WorldScene scene) ...       -- WorldScene
    foldr:
     ... new DrawInv(), Invader i1, WorldScene scene ...             -- WorldScene
     ... new CountInv(), Invader i1, Integer count ...               -- int
    filter:
     ... new InvaderDie() Invader i1, IList<Bullet> lob ...             -- boolean
    ormap:
     ... new CheckCollision(), Invader i1, Bullet b ...             -- boolean
    ormap2:
     ... new InvExists(), Invader i1, Integer int1, Integer int2 ...   -- boolean

     */

    return loi.ormap(new CheckCollision(), b);

  }
}



//Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc4<A1, A2> {
  boolean apply(A1 arg1, A2 arg2);
}


//class where the apply function checks if the invader has been hit
class CheckHits implements IFunc4<Bullet, Invader> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Bullet b, Invader i1) ...       -- boolean
   */

  //checks if the invader has been hit
  public boolean apply(Bullet b, Invader i1) {

    /*
    TEMPLATE
    PARAMETERS
    ... b ...            -- Bullet
    ... i1 ...           -- Invader

    METHODS FOR PARAMETERS
    ... this.b.draw(WorldScene scene) ...       -- WorldScene
    ... this.i1.draw(WorldScene scene) ...       -- WorldScene
     */

    return b.isShips && Math.abs(b.x - i1.x) <= (i1.invaderWidth / 2 + b.radius)
        && Math.abs(b.y - i1.y) <= (i1.invaderHeight / 2 + b.radius);

  }
}

//class where the apply function checks if the Spaceship has been hit
class CheckLose implements IFunc4<Bullet, Spaceship> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Bullet b, Spaceship s) ...       -- boolean
   */

  //checks if the Spaceship has been hit
  public boolean apply(Bullet b, Spaceship s) {

    /*
    TEMPLATE
    PARAMETERS
    ... b ...            -- Bullet
    ... s ...            -- Spaceship

    METHODS FOR PARAMETERS
    ... this.b.draw(WorldScene scene) ...       -- WorldScene
    ... this.s.move() ...                       -- Spaceship
    ... this.s.turn() ...                       -- Spaceship
    ... this.s.draw(WorldScene scene) ...       -- WorldScene
    ... this.s.maker() ...                      -- Bullet
this.s.generate(int a, int b, int xGap, int yGap, int startingX, IList<Bullet> lob) --IList<Bullet>
     */

    return !b.isShips && Math.abs(b.x - s.x) <= (s.shipWidth / 2 + b.radius)
        && Math.abs(b.y - s.y) <= (s.shipHeight / 2 + b.radius);

  }
}

//class where the apply function checks if the bullet has collided with an invader
class CheckCollision implements IFunc4<Invader, Bullet> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Invader i1, Bullet b) ...       -- boolean
   */

  //checks if the bullet has collided with an invader
  public boolean apply(Invader i1, Bullet b) {

    /*
    TEMPLATE
    PARAMETERS
    ... b ...            -- Bullet
    ... i1 ...           -- Invader

    METHODS FOR PARAMETERS
    ... this.b.draw(WorldScene scene) ...       -- WorldScene
    ... this.i1.draw(WorldScene scene) ...       -- WorldScene
     */

    return b.isShips && Math.abs(b.x - i1.x) <= (i1.invaderWidth / 2 + b.radius) 
        && Math.abs(b.y - i1.y) <= (i1.invaderHeight / 2 + b.radius);
  }
}


//Interface for three-argument function-objects with signature [A1, A2 -> R]
interface IFunc5<A1, A2, A3> {
  boolean apply(A1 arg1, A2 arg2, A3 arg3);
}

//class where apply function checks if the bullet has collided with an invader
class InvExists implements IFunc5<Invader, Integer, Integer> {

  /*
  TEMPLATE

  FIELDS
   n/a

  METHODS
  ... this.apply(Invader i1, Integer int1, Integer int2 ...       -- boolean
   */

  //checks if the bullet has collided with an invader
  public boolean apply(Invader i1, Integer int1, Integer int2) {

    /*
    TEMPLATE
    PARAMETERS
    ... i1 ...           -- Invader
    ... int1 ...         -- Integer
    ... int2 ...         -- Integer

    METHODS FOR PARAMETERS
    ... this.i1.draw(WorldScene scene) ...       -- WorldScene
     */

    return i1.x == int1 && i1.y == int2;
  }
}





//this class represents a single spaceship
class Spaceship {
  Color shipColor = Color.BLACK;
  int sceneWidth = 1000;
  int sceneHeight = 1000;
  int shipWidth = 150;
  int shipHeight = 20;
  int y = 700;
  int speedX;
  int x;



  Spaceship(int x, int speedX) {
    this.x = x;
    this.speedX = speedX;
  }

  /*
  TEMPLATE
  FIELDS:
  ... this.shipColor ...           -- Color
  ... sceneWidth ...               -- int
  ... sceneHeight ...              -- int
  ... shipWidth ...                -- int
  ... shipHeight ...               -- int
  ... this.y ...                   -- int
  ... speedX ...                   -- int
  ... this.x ...                   -- int

  METHODS
  ... this.move() ...                       -- Spaceship
  ... this.turn() ...                       -- Spaceship
  ... this.draw(WorldScene scene) ...       -- WorldScene
  ... this.maker() ...                      -- Bullet
  this.generate(int a, int b, int xGap, int yGap, int startingX, IList<Bullet> lob) --IList<Bullet>

  METHODS FOR FIELDS
  n/a
   */


  //Create a new ship created by moving this ship by its speed, check with window bounds
  Spaceship move() { 
    //Template: same as class template
    int newX = this.x + this.speedX;

    if (newX >= sceneWidth) {
      return new Spaceship(sceneWidth, this.speedX);
    }
    else if (newX <= 0) {
      return new Spaceship(0, this.speedX);
    }
    else {
      return new Spaceship(newX, this.speedX);
    }
  }

  //Create a new ship that is now going in the opposite direction
  Spaceship turn() { 
    //Template: same as class template
    return new Spaceship(this.x, -1 * this.speedX);
  }


  //draw this ship as a rectangular image in the provided scene
  WorldScene draw(WorldScene scene) {
    /*
    TEMPLATE
    same as class

    PARAMETERS
    ... scene ...       -- WorldScene
     */

    return scene.placeImageXY(new RectangleImage(shipWidth, shipHeight, OutlineMode.SOLID,
        shipColor), this.x, y);
  }

  //draw this ship as a rectangular image in the provided scene
  Bullet maker() {
    //Template: same as class template
    return new Bullet(this.x, this.y, true);
  }


  //Generates opposing bullets
  IList<Bullet> generate(int a, int b, int xGap, int yGap, int startingX, IList<Bullet> lob) {

    /*
    TEMPLATE
    same as class

    PARAMETERS
    ... a ...               -- int
    ... b ...               -- int
    ... xGap ...            -- int
    ... yGap ...            -- int
    ... startingX ...       -- int
    ... lob ...             -- IList<Bullet>

    METHODS OF PARAMETERS
    foldr:
     ... new DrawBullets(), Bullet b, WorldScene scene ...             -- boolean
     ... new CountSB(), Bullet b, Integer count ...             -- int
     ... new CountIB(), Bullet b, Integer count ...             -- int
     ... new FilterFrame(), Bullet b, IList<Bullet> lob...             -- IList<Bullet>
    map:
     ... new MoveBullet(), Bullet b...                        -- IList<Bullet>
    filter:
     ... new BulletDie() Bullet b, IList<Invader> loi ...             -- IList<Bullet>
    ormap:
     ... new CheckLose(), Bullet b, Spaceship s ...             -- boolean
     ... new CheckHit().apply(Bullet b, Invader i1) ...             -- boolean

     */

    return new ConsList<Bullet>(new Bullet(a * xGap + startingX, b * yGap, false), lob);
  }
}





//this class represents a single invader
class Invader {
  Color invaderColor = Color.RED;
  int sceneWidth = 1000;
  int sceneHeight = 1000;
  int invaderWidth = 20;
  int invaderHeight = 20;
  int y;
  int x;

  /*
  TEMPLATE
  FIELDS:
  ... this.invaderColor ...        -- Color
  ... sceneWidth ...               -- int
  ... sceneHeight ...              -- int
  ... invaderWidth ...             -- int
  ... invaderHeight ...            -- int
  ... this.y ...                   -- int
  ... this.x ...                   -- int

  METHODS
  ... this.draw(WorldScene scene) ...       -- WorldScene

  METHODS FOR FIELDS
  n/a
   */

  Invader(int x, int y) {
    this.x = x;
    this.y = y;
  }

  //draw this ship as a rectangular image in the provided scene
  WorldScene draw(WorldScene scene) {

    /*
    TEMPLATE
    same as class

    PARAMETERS
    ... scene ...       -- WorldScene
     */

    return scene.placeImageXY(new RectangleImage(invaderWidth, invaderHeight, OutlineMode.SOLID,
        invaderColor), x, y);
  }
}



//this class represents a single invader
class Bullet {
  Color invaderColor = Color.RED;
  Color shipColor = Color.BLACK;
  int sceneWidth = 1000;
  int sceneHeight = 1000;
  int radius = 3;
  int speedY = 7;
  int y;
  int x;
  boolean isShips;

  Bullet(int x, int y, boolean isShips) {
    this.x = x;
    this.y = y;
    this.isShips = isShips;
  }

  /*
  TEMPLATE
  FIELDS:
  ... this.invaderColor ...        -- Color
  ... this.shipColor ...           -- Color
  ... sceneWidth ...               -- int
  ... sceneHeight ...              -- int
  ... radius ...                   -- int
  ... speedY ...                   -- int
  ... this.y ...                   -- int
  ... this.x ...                   -- int
  ... this.isShips ...             -- boolean
  METHODS
  ... this.draw(WorldScene scene) ...       -- WorldScene
  ... this.move() ...                       -- Bullet

  METHODS FOR FIELDS
  n/a
   */

  //draw this bullet as a circular image in the provided scene
  WorldScene draw(WorldScene scene) {

    /*
    TEMPLATE
    same as class

    PARAMETERS
    ... scene ...       -- WorldScene
     */

    if (this.isShips) {
      return scene.placeImageXY(
          new CircleImage(this.radius, OutlineMode.SOLID, this.shipColor), x, y);
    }
    else {
      return scene.placeImageXY(
          new CircleImage(this.radius, OutlineMode.SOLID, this.invaderColor), x, y);
    }
  }

  //Return a new bullet created by moving this bullet by its speed
  Bullet move() { 
    //Template: same as class template
    if (this.isShips) {
      return new Bullet(this.x,  this.y - this.speedY, this.isShips);
    }
    else {
      return new Bullet(this.x, this.speedY + this.y, this.isShips);
    }
  }

}





//This particular assignment uses the "funworld" version of the library (see imports above). 
//Represents the World of the Space-invaders game
class ShipWorld extends World {
  int sceneWidth = 1000; //scene is a square
  int speedShip = 5;
  int xInvGap = 70;
  int yInvGap = 70;
  int startingX = 150; 
  int iLim = 3; 
  int sLim = 10; 

  WorldImage lose = new TextImage("Game over!", 24, FontStyle.BOLD, Color.RED);
  WorldScene loseScreen = new WorldScene(sceneWidth, sceneWidth).placeImageXY(
      lose, sceneWidth / 2, sceneWidth / 2);
  WorldImage win = new TextImage("You won!", 24, FontStyle.BOLD, Color.GREEN);
  WorldScene winScreen = new WorldScene(sceneWidth, sceneWidth).placeImageXY(
      win, sceneWidth / 2, sceneWidth / 2);


  int randX = new Random().nextInt(8) + 1;
  int randY = new Random().nextInt(3) + 1;

  Spaceship ship;
  IList<Invader> loInv; 
  IList<Bullet> loBullet;

  /*
  TEMPLATE
  FIELDS:
  ... this.ship ...            -- Spaceship
  ... this.loInv ...           -- IList<Invader>
  ... this.loBullet ...           -- IList<Bullet>
  ... this.sceneWidth ...               -- int
  ... this.speedShip ...                -- int
  ... this.xInvGap ...                  -- int
  ... this.yInvGap ...                  -- int
  ... this.startingX ...                -- int
  ... this.iLim ...                     -- int
  ... this.sLim ...                     -- int
  ... this.randX ...                    -- int
  ... this.randY ...                    -- int
  ... this.lose ...                   -- WorldImage
  ... this.loseScreen ...             -- WorldScene
  ... this.win ...                    -- WorldImage
  ... this.winScreen ...              -- WorldScene
  METHODS
  ... this.makeScene() ...            -- WorldScene
  ... this.onTick() ...               -- World
  ... this.worldEnds() ...            -- WorldEnd
  ... this.onKeyReleased(String key) ...            -- World

  METHODS FOR FIELDS
  ... this.ship.move() ...                       -- Spaceship
  ... this.ship.turn() ...                       -- Spaceship
  ... this.ship.draw(WorldScene scene) ...       -- WorldScene
  ... this.ship.maker() ...                      -- Bullet
  this.ship.generate(int a, int b, int xGap, int yGap, int startingX, IList<Bullet> lob) 
  --IList<Bullet>


   this.loInv.foldr:
     ... new DrawInv(), Invader i1, WorldScene scene ...             -- WorldScene
     ... new CountInv(), Invader i1, Integer count ...               -- int
   this.loInv.filter:
     ... new InvaderDie() Invader i1, IList<Bullet> lob ...             -- boolean
   this.loInv.ormap:
     ... new CheckCollision(), Invader i1, Bullet b ...             -- boolean
   this.loInv.ormap2:
     ... new InvExists(), Invader i1, Integer int1, Integer int2 ...   -- boolean


  this.loBullet.foldr:
     ... new DrawBullets(), Bullet b, WorldScene scene ...             -- boolean
     ... new CountSB(), Bullet b, Integer count ...             -- int
     ... new CountIB(), Bullet b, Integer count ...             -- int
     ... new FilterFrame(), Bullet b, IList<Bullet> lob...             -- IList<Bullet>
  this.loBullet.map:
     ... new MoveBullet(), Bullet b...                        -- IList<Bullet>
    filter:
     ... new BulletDie() Bullet b, IList<Invader> loi ...             -- IList<Bullet>
  this.loBullet.ormap:
     ... new CheckLose(), Bullet b, Spaceship s ...             -- boolean
     ... new CheckHit().apply(Bullet b, Invader i1) ...             -- boolean
   */


  //constructor with all fields with starting values
  ShipWorld() {
    this.ship = new Spaceship(sceneWidth / 2, speedShip);
    this.loBullet = new MtList<Bullet>();
    this.loInv = new ConsList<Invader>(new Invader(xInvGap + startingX, yInvGap),
        new ConsList<Invader>(new Invader(xInvGap * 2 + startingX, yInvGap), 
            new ConsList<Invader>(new Invader(xInvGap * 3 + startingX, yInvGap), 
                new ConsList<Invader>(new Invader(xInvGap * 4 + startingX, yInvGap), 
                    new ConsList<Invader>(new Invader(xInvGap * 5 + startingX, yInvGap),
                        new ConsList<Invader>(new Invader(xInvGap * 6 + startingX, yInvGap), 
                            new ConsList<Invader>(new Invader(xInvGap * 7 + startingX, yInvGap), 
                                new ConsList<Invader>(
                                    new Invader(xInvGap * 8 + startingX, yInvGap), 
    new ConsList<Invader>( new Invader(xInvGap * 9 + startingX, yInvGap),
        new ConsList<Invader>(new Invader(xInvGap + startingX, yInvGap * 2),
                new ConsList<Invader>(new Invader(xInvGap * 2 + startingX, yInvGap * 2),
                    new ConsList<Invader>(new Invader(xInvGap * 3 + startingX, yInvGap * 2), 
                        new ConsList<Invader>(new Invader(xInvGap * 4 + startingX, yInvGap * 2), 
                            new ConsList<Invader>(
                                new Invader(xInvGap * 5 + startingX, yInvGap * 2),
                                new ConsList<Invader>(
                                    new Invader(xInvGap * 6 + startingX, yInvGap * 2),
                                    new ConsList<Invader>(
                                        new Invader(xInvGap * 7 + startingX, yInvGap * 2), 
                                        new ConsList<Invader>(
                                            new Invader(xInvGap * 8 + startingX, yInvGap * 2),
                                            new ConsList<Invader>(
                                                new Invader(xInvGap * 9 + startingX, yInvGap * 2),
    new ConsList<Invader>(new Invader(xInvGap + startingX, yInvGap * 3),
        new ConsList<Invader>(new Invader(xInvGap * 2 + startingX, yInvGap * 3), 
            new ConsList<Invader>(new Invader(xInvGap * 3 + startingX, yInvGap * 3), 
                new ConsList<Invader>(new Invader(xInvGap * 4 + startingX, yInvGap * 3), 
                    new ConsList<Invader>(new Invader(xInvGap * 5 + startingX, yInvGap * 3),
                        new ConsList<Invader>(new Invader(xInvGap * 6 + startingX, yInvGap * 3), 
                            new ConsList<Invader>(
                                new Invader(xInvGap * 7 + startingX, yInvGap * 3), 
                                new ConsList<Invader>(
                                    new Invader(xInvGap * 8 + startingX, yInvGap * 3),
                                    new ConsList<Invader>(
                                        new Invader(xInvGap * 9 + startingX, yInvGap * 3),
    new ConsList<Invader>(new Invader(xInvGap + startingX, yInvGap * 4),
            new ConsList<Invader>(new Invader(xInvGap * 2 + startingX, yInvGap * 4),
                new ConsList<Invader>(new Invader(xInvGap * 3 + startingX, yInvGap * 4), 
                    new ConsList<Invader>(new Invader(xInvGap * 4 + startingX, yInvGap * 4), 
                        new ConsList<Invader>(new Invader(xInvGap * 5 + startingX, yInvGap * 4),
                            new ConsList<Invader>(
                                new Invader(xInvGap * 6 + startingX, yInvGap * 4),
                                new ConsList<Invader>(
                                    new Invader(xInvGap * 7 + startingX, yInvGap * 4), 
                                    new ConsList<Invader>(
                                        new Invader(xInvGap * 8 + startingX, yInvGap * 4),
                                        new ConsList<Invader>(
                                            new Invader(xInvGap * 9 + startingX, yInvGap * 4),
                                            new MtList<Invader>()))))))))))))))))))))))))))))))))))
                    ));

  }

  //constructor for tests w/ random numbers, spaceship, ilist<invader> & ilist<bullet> set by user
  ShipWorld(Spaceship s,  IList<Invader> loi, IList<Bullet> lob, int r1, int r2) {
    this.randX = r1;
    this.randY = r2;
    this.ship = s;
    this.loInv = loi;
    this.loBullet = lob;
  }
  
  //constructor with spaceship, ilist<invader> and ilist<bullet> set by user
  ShipWorld(Spaceship s, IList<Invader> loi, IList<Bullet> lob) {
    this(s, loi, lob, new Random().nextInt(8) + 1, new Random().nextInt(3) + 1);
  }




  @Override
  //Draw the bullets, invaders and ship in the scene
  public WorldScene makeScene() {
    //Template: same as class template
    WorldScene scene = this.getEmptyScene();

    return loBullet.foldr(new DrawBullets(), loInv.foldr(new DrawInv(), this.ship.draw(scene)));
  }
  

  @Override
  //@ every tick, update game by moving & generating necessary pieces in accordance with the rules
  public ShipWorld onTick() {
    //Template: same as class template
    if (this.loBullet.foldr(new FilterFrame(), new MtList<Bullet>()).foldr(new CountIB(), 0) < iLim
        && this.loInv.ormap2(new InvExists(), randX * xInvGap +  startingX, randY * yInvGap)) {

      return new ShipWorld(this.ship.move(), this.loInv.filter(new InvaderDie(), this.loBullet), 
          this.ship.generate(randX, randY, xInvGap, yInvGap, startingX,
              this.loBullet.filter(new BulletDie(), this.loInv).map(new MoveBullet())));
    }
    else {
      return new ShipWorld(this.ship.move(), this.loInv.filter(new InvaderDie(), this.loBullet), 
          this.loBullet.filter(new BulletDie(), this.loInv).map(new MoveBullet()));
    }
  }

  //if ship hit, lose game. if all invaders hit, win game. otherwise, continue game.
  public WorldEnd worldEnds() {
    //Template: same as class template

    if (this.loBullet.ormap(new CheckLose(), this.ship)) {
      return new WorldEnd(true, loseScreen);
    } 
    else if (this.loInv.foldr(new CountInv(), 0) == 0) {
      return new WorldEnd(true, winScreen);
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }



  
  @Override
  //If side arrow keys pressed, turn ship direction around. If space pushed, shoot ship bullet.
  public World onKeyReleased(String key) {

    /*
    TEMPLATE
    same as class

    PARAMETERS
    ... key ...       -- String
     */

    if (key.equals("left") || key.equals("right")) {
      //create a new ship if direction change and return the resulting world 
      //or keep same world otherwise
      return new ShipWorld(this.ship.turn(), this.loInv, this.loBullet, this.randX, this.randY);
    }
    else if (key.contentEquals(" ") && this.loBullet.foldr(new FilterFrame(), 
        new MtList<Bullet>()).foldr(new CountSB(), 0) < sLim) {
      return new ShipWorld(this.ship, this.loInv, new ConsList<Bullet>(this.ship.maker(),
          this.loBullet), this.randX, this.randY);
    }
    else {
      return this;
    }

  }
}

//Tests and Examples for the Space Invader game
class ExamplesShipWorld {

  int sceneWidth = 1000; //scene is a square
  Color invaderColor = Color.RED;
  int invaderWidth = 20;
  int invaderHeight = 20;
  int randX = 4;
  int randY = 4;
  int xInvGap = 70;
  int yInvGap = 70;
  int startingX = 150;

  WorldScene scene = new ShipWorld().getEmptyScene();
  WorldImage lose = new TextImage("Game over!", 24, FontStyle.BOLD, Color.RED);
  WorldScene loseScreen = new WorldScene(sceneWidth, sceneWidth).placeImageXY(
      lose, sceneWidth / 2, sceneWidth / 2);
  WorldImage win = new TextImage("You won!", 24, FontStyle.BOLD, Color.GREEN);
  WorldScene winScreen = new WorldScene(sceneWidth, sceneWidth).placeImageXY(
      win, sceneWidth / 2, sceneWidth / 2);
  
  Spaceship ship1 = new Spaceship(500, -5);
  Spaceship ship2 = new Spaceship(0, 5);
  Spaceship ship3 = new Spaceship(500, 5);
  Spaceship ship4 = new Spaceship(110, -9);
  Spaceship ship5 = new Spaceship(50, 7);

  Invader invader1 = new Invader(50, 300);
  Invader invader2 = new Invader(40, 200);
  Invader invader3 = new Invader(60, 400);
  
  Bullet bullet1 = new Bullet(50, 300, true);
  Bullet bullet2 = new Bullet(40, 200, true);
  Bullet bullet3 = new Bullet(60, 400, false);
  Bullet bullet4 = new Bullet(55, 390, true);
  Bullet bullet5 = new Bullet(50, 700, false);
  
  Bullet bullet1t = new Bullet(100, 300, true);
  Bullet bullet2t = new Bullet(200, 400, true);
  Bullet bullet1f = new Bullet(100, 200, false);
  Bullet bullet2f = new Bullet(50, 300, false);
  Bullet bullet3f = new Bullet(500, 700, false);
  
  Bullet bulletBdr1 = new Bullet(0, 0, true);
  Bullet bulletBdr2 = new Bullet(1000, 1000, false);
  Bullet bulletBad1 = new Bullet(1001, 300, true);
  Bullet bulletBad2 = new Bullet(-1, 400, false);
  Bullet bulletBad3 = new Bullet(100, 10001, true);
  Bullet bulletBad4 = new Bullet(50, -1, false);
  

  IList<Bullet> loBullet1 = new ConsList<Bullet>(bullet1t, new ConsList<Bullet>(bullet1f, 
      new ConsList<Bullet>(bullet2f, new MtList<Bullet>())));
  IList<Bullet> loBulletFilter = new ConsList<Bullet>(bullet1, new ConsList<Bullet>(bullet2, 
      new ConsList<Bullet>(bullet3,new MtList<Bullet>())));
  IList<Bullet> loBulletFilter2 = new ConsList<Bullet>(bullet1, new ConsList<Bullet>(bullet4,
      new MtList<Bullet>()));
  IList<Bullet> loBulletFilter3 = new ConsList<Bullet>(bullet5, loBulletFilter2);
  IList<Bullet> loBullet2 = new ConsList<Bullet>(bullet1t, new ConsList<Bullet>(bullet2t,
      new ConsList<Bullet>(bullet1f, new MtList<Bullet>())));
  IList<Bullet> loBullet3 = new ConsList<Bullet>(bulletBdr1, new ConsList<Bullet>(bulletBad4,
      new ConsList<Bullet>(bulletBdr2, new ConsList<Bullet>(bulletBad3, 
          new ConsList<Bullet>(bulletBad2, new ConsList<Bullet>(bulletBad1, 
              new MtList<Bullet>()))))));
  IList<Bullet> loBullet4 = new ConsList<Bullet>(bullet1, new ConsList<Bullet>(bullet2, 
      new ConsList<Bullet>(bullet1, new ConsList<Bullet>(bullet1, new ConsList<Bullet>(bullet2, 
      new ConsList<Bullet>(bullet1, new ConsList<Bullet>(bullet1, new ConsList<Bullet>(bullet2, 
      new ConsList<Bullet>(bullet1, loBulletFilter)))))))));
  IList<Bullet> loBullet5 = new ConsList<Bullet>(bullet1f, new ConsList<Bullet>(bullet2f, 
      new ConsList<Bullet>(bullet1f, new ConsList<Bullet>(bullet1f, new ConsList<Bullet>(bullet2f, 
      new ConsList<Bullet>(bullet1f, new ConsList<Bullet>(bullet1f, new ConsList<Bullet>(bullet2f, 
      new ConsList<Bullet>(bullet1f, loBulletFilter)))))))));
  IList<Bullet> loBulletLose = new ConsList<Bullet>(bullet1t, new ConsList<Bullet>(bullet3f, 
      new ConsList<Bullet>(bullet2f, new MtList<Bullet>())));
  

  
  IList<Invader> loInv1 = new ConsList<Invader>(invader1, new ConsList<Invader>(invader2,
      new MtList<Invader>()));
  
  IList<Invader> loInv2 = new ConsList<Invader>(invader3, loInv1);
  
  IList<Invader> starter = new ConsList<Invader>(new Invader(xInvGap + startingX, yInvGap),
      new ConsList<Invader>(new Invader(xInvGap * 2 + startingX, yInvGap), 
          new ConsList<Invader>(new Invader(xInvGap * 3 + startingX, yInvGap), 
              new ConsList<Invader>(new Invader(xInvGap * 4 + startingX, yInvGap), 
                  new ConsList<Invader>(new Invader(xInvGap * 5 + startingX, yInvGap),
                      new ConsList<Invader>(new Invader(xInvGap * 6 + startingX, yInvGap), 
                          new ConsList<Invader>(new Invader(xInvGap * 7 + startingX, yInvGap), 
                              new ConsList<Invader>(
                                  new Invader(xInvGap * 8 + startingX, yInvGap), 
      new ConsList<Invader>( new Invader(xInvGap * 9 + startingX, yInvGap),
      new ConsList<Invader>(new Invader(xInvGap + startingX, yInvGap * 2),
              new ConsList<Invader>(new Invader(xInvGap * 2 + startingX, yInvGap * 2),
                  new ConsList<Invader>(new Invader(xInvGap * 3 + startingX, yInvGap * 2), 
                      new ConsList<Invader>(new Invader(xInvGap * 4 + startingX, yInvGap * 2), 
                          new ConsList<Invader>(
                              new Invader(xInvGap * 5 + startingX, yInvGap * 2),
                              new ConsList<Invader>(
                                  new Invader(xInvGap * 6 + startingX, yInvGap * 2),
                                  new ConsList<Invader>(
                                      new Invader(xInvGap * 7 + startingX, yInvGap * 2), 
                                      new ConsList<Invader>(
                                          new Invader(xInvGap * 8 + startingX, yInvGap * 2),
                                          new ConsList<Invader>(
                                              new Invader(xInvGap * 9 + startingX, yInvGap * 2),
      new ConsList<Invader>(new Invader(xInvGap + startingX, yInvGap * 3),
      new ConsList<Invader>(new Invader(xInvGap * 2 + startingX, yInvGap * 3), 
          new ConsList<Invader>(new Invader(xInvGap * 3 + startingX, yInvGap * 3), 
              new ConsList<Invader>(new Invader(xInvGap * 4 + startingX, yInvGap * 3), 
                  new ConsList<Invader>(new Invader(xInvGap * 5 + startingX, yInvGap * 3),
                      new ConsList<Invader>(new Invader(xInvGap * 6 + startingX, yInvGap * 3), 
                          new ConsList<Invader>(
                              new Invader(xInvGap * 7 + startingX, yInvGap * 3), 
                              new ConsList<Invader>(
                                  new Invader(xInvGap * 8 + startingX, yInvGap * 3),
                                  new ConsList<Invader>(
                                      new Invader(xInvGap * 9 + startingX, yInvGap * 3),
      new ConsList<Invader>(new Invader(xInvGap + startingX, yInvGap * 4),
          new ConsList<Invader>(new Invader(xInvGap * 2 + startingX, yInvGap * 4),
              new ConsList<Invader>(new Invader(xInvGap * 3 + startingX, yInvGap * 4), 
                  new ConsList<Invader>(new Invader(xInvGap * 4 + startingX, yInvGap * 4), 
                      new ConsList<Invader>(new Invader(xInvGap * 5 + startingX, yInvGap * 4),
                          new ConsList<Invader>(
                              new Invader(xInvGap * 6 + startingX, yInvGap * 4),
                              new ConsList<Invader>(
                                  new Invader(xInvGap * 7 + startingX, yInvGap * 4), 
                                  new ConsList<Invader>(
                                      new Invader(xInvGap * 8 + startingX, yInvGap * 4),
                                      new ConsList<Invader>(
                                          new Invader(xInvGap * 9 + startingX, yInvGap * 4),
                                          new MtList<Invader>()))))))))))))))))))))))))))))))))))
                  ));


  ShipWorld forTesting = new ShipWorld(ship1, starter, loBullet1, randX, randY);
  ShipWorld forTesting1 = new ShipWorld(ship2, starter, loBullet4, randX, randY);
  ShipWorld forTesting2 = new ShipWorld(ship2, starter, loBullet5, randX, randY);
  ShipWorld forTesting3 = new ShipWorld(ship2, loInv1, loBullet1, randX, randY);
  //setting these as variables and accessing their random variables in tests
  ShipWorld tickTest1 = forTesting.onTick(); 
  ShipWorld tickTest2 = forTesting2.onTick();
  ShipWorld tickTest3 = forTesting3.onTick();
  ShipWorld gameLose = new ShipWorld(ship1, starter, loBulletLose);
  ShipWorld gameWin = new ShipWorld(ship1, new MtList<Invader>(), loBullet1);
  
  //test the foldr method
  boolean testFoldr(Tester t) {
    return t.checkExpect(loInv1.foldr(new DrawInv(), winScreen), 
        winScreen.placeImageXY(new RectangleImage(invaderWidth, invaderHeight, OutlineMode.SOLID,
            invaderColor), 50, 300).placeImageXY(new RectangleImage(
                invaderWidth, invaderHeight, OutlineMode.SOLID,invaderColor), 40, 200))
        && t.checkExpect(new MtList<Invader>().foldr(new DrawInv(), loseScreen), loseScreen)
        && t.checkExpect(loBullet1.foldr(new DrawBullets(), loseScreen), 
            bullet2f.draw(bullet1f.draw(bullet1t.draw(loseScreen))))
        && t.checkExpect(new MtList<Invader>().foldr(new DrawInv(), winScreen), winScreen)
        && t.checkExpect(loBullet2.foldr(new CountSB(), 0), 2)
        && t.checkExpect(new MtList<Bullet>().foldr(new CountSB(), 1), 1)
        && t.checkExpect(loBullet2.foldr(new CountIB(), 0), 1)
        && t.checkExpect(new MtList<Bullet>().foldr(new CountIB(), 1), 1)
        && t.checkExpect(loInv1.foldr(new CountInv(), 0), 2)
        && t.checkExpect(starter.foldr(new CountInv(), 1), 37)
        && t.checkExpect(new MtList<Bullet>().foldr(new CountIB(), 0), 0)
        && t.checkExpect(loBullet3.foldr(new FilterFrame(), new MtList<Bullet>()), 
            new ConsList<Bullet>(bulletBdr1, new ConsList<Bullet>(bulletBdr2, 
                new MtList<Bullet>())))
        && t.checkExpect(new MtList<Bullet>().foldr(new FilterFrame(), loBullet3), loBullet3);
  }
  
  //test the map method
  boolean testMap(Tester t) {
    return t.checkExpect(loBullet2.map(new MoveBullet()), 
        new ConsList<Bullet>(bullet1t.move(), new ConsList<Bullet>(bullet2t.move(),
            new ConsList<Bullet>(bullet1f.move(), new MtList<Bullet>()))))
        && t.checkExpect(new MtList<Bullet>().map(new MoveBullet()), new MtList<Bullet>());
  }
  
  //test the filter method
  boolean testFilter(Tester t) {
    return t.checkExpect(loInv1.filter(new InvaderDie(), loBulletFilter), new MtList<Invader>())
        && t.checkExpect(loInv1.filter(new InvaderDie(), loBullet1), loInv1)
        && t.checkExpect(new MtList<Invader>().filter(new InvaderDie(), loBullet1), 
            new MtList<Invader>())
        && t.checkExpect(loBulletFilter.filter(new BulletDie(), loInv1), 
            new ConsList<Bullet>(bullet3, new MtList<Bullet>()))
        && t.checkExpect(loBullet1.filter(new BulletDie(), loInv1), loBullet1)
        && t.checkExpect(new MtList<Bullet>().filter(new BulletDie(), loInv1),
            new MtList<Bullet>());
  }
  
  //test the ormap method
  boolean testOrMap(Tester t) {
    return t.checkExpect(loBullet1.ormap(new CheckHits(), invader1), false)
        && t.checkExpect(loBulletFilter.ormap(new CheckHits(), invader2), true)
        && t.checkExpect(loBulletFilter2.ormap(new CheckHits(), invader3), true)
        && t.checkExpect(new MtList<Bullet>().ormap(new CheckHits(), invader3), false)
        && t.checkExpect(loBullet1.ormap(new CheckLose(), ship4), false)
        && t.checkExpect(loBulletFilter.ormap(new CheckLose(), ship3), false)
        && t.checkExpect(loBulletFilter3.ormap(new CheckLose(), ship5), true)
        && t.checkExpect(new MtList<Bullet>().ormap(new CheckLose(), ship5), false)
        && t.checkExpect(loInv1.ormap(new CheckCollision(), bullet1), true)
        && t.checkExpect(loInv1.ormap(new CheckCollision(), bullet1f), false)
        && t.checkExpect(loInv2.ormap(new CheckCollision(), bullet4), true)
        && t.checkExpect(new MtList<Invader>().ormap(new CheckCollision(), bullet2), false);
  }
  
  //test the ormap2 method
  boolean testOrMap2(Tester t) {
    return t.checkExpect(loInv1.ormap2(new InvExists(), 40, 200), true)
        && t.checkExpect(loInv1.ormap2(new InvExists(), 41, 200), false)
        && t.checkExpect(loInv1.ormap2(new InvExists(), 40, 199), false)
        && t.checkExpect(new MtList<Invader>().ormap2(new InvExists(), 40, 199), false);
  }
  
  //test the onKeyReleased method
  boolean testOnKey(Tester t) {
    return t.checkExpect(forTesting.onKeyReleased("right"), new ShipWorld(ship3, starter, 
        loBullet1, randX, randY))
        && t.checkExpect(forTesting.onKeyReleased("left"), new ShipWorld(ship3, starter, 
            loBullet1, randX, randY))
        && t.checkExpect(forTesting.onKeyReleased(" "), new ShipWorld(ship1, starter, 
            new ConsList<Bullet>(ship3.maker(),loBullet1), randX, randY))
        && t.checkExpect(forTesting1.onKeyReleased(" "), forTesting1);
  }

    
  //test the onTick method
  boolean testOnTick(Tester t) {
    return t.checkExpect(tickTest1, new ShipWorld(this.ship1.move(),
        this.starter.filter(new InvaderDie(), this.loBullet1), 
        this.ship1.generate(randX, randY, xInvGap, yInvGap, startingX,
            this.loBullet1.filter(new BulletDie(), this.starter).map(new MoveBullet())), 
        tickTest1.randX, tickTest1.randY))
        && t.checkExpect(tickTest2, new ShipWorld(this.ship2.move(), 
            this.starter.filter(new InvaderDie(), this.loBullet5), 
            this.loBullet5.filter(new BulletDie(), this.starter).map(new MoveBullet()), 
            tickTest2.randX, tickTest2.randY))
        && t.checkExpect(tickTest3, new ShipWorld(this.ship2.move(), 
            this.loInv1.filter(new InvaderDie(), this.loBullet1), 
            this.loBullet1.filter(new BulletDie(), this.loInv1).map(new MoveBullet()), 
            tickTest3.randX, tickTest3.randY));
  }

  //test the makeScene method
  boolean testMakeScene(Tester t) {
    return t.checkExpect(forTesting.makeScene(), loBullet1.foldr(new DrawBullets(), 
        starter.foldr(new DrawInv(), this.ship1.draw(scene))))
        && t.checkExpect(forTesting1.makeScene(), loBullet4.foldr(new DrawBullets(), 
            starter.foldr(new DrawInv(), this.ship2.draw(scene))));
  }
    
  //test the worldEnds method
  boolean testWorldEnds(Tester t) {
    return t.checkExpect(gameLose.worldEnds(), new WorldEnd(true, loseScreen))
        && t.checkExpect(gameWin.worldEnds(), new WorldEnd(true, winScreen))
        && t.checkExpect(forTesting.worldEnds(), new WorldEnd(false, 
            loBullet1.foldr(new DrawBullets(), starter.foldr(new DrawInv(), 
                this.ship1.draw(scene)))));
  }

  //running game
  boolean testGo(Tester t) {
    //create my world. This will initialize everything
    ShipWorld myWorld = new ShipWorld();

    return myWorld.bigBang(1000,1000,0.1);
  }
}
