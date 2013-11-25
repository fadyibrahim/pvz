/**
 * 
 */
package model;

/**
 * @author Abhinav Thukral
 * ExplosiveZombie creates a zombie which has double the attack power of DefZombie and when the Zombie dies, it also kills the next plant in line
 *
 */
public class ExplosiveZombie extends Zombie {
	// Default Health Factor multiplies with level to increase max health
	private static final int HF = 50; 
	// Default Damage Factor multiplies with level to increase damage 
	private static final int DF = 20;
	// Default Sprite for the Zombie
	private static final String DEFSPRITE = "";
	// Cracked Sprite for the Zombie
	private static final String CRACKEDSPRITE = "";
	/**
	 * @param level
	 */
	public ExplosiveZombie(int level) {
		super((HF * level), level, "ZE");
		super.sprite = DEFSPRITE;
		super.crackedSprite = CRACKEDSPRITE;
	}

	/* (non-Javadoc)
	 * @see model.Zombie#attack(model.Actor)
	 */
	@Override
	protected void attack(Actor actor) {
		actor.takeDamage(DF * super.level);
	}

	/* (non-Javadoc)
	 * @see model.Actor#act()
	 */
	@Override
	public int act() {
		int move = super.move();
		if(move == 0){
			Actor actor = tile.getLeft().getOccupant();
			if (actor instanceof Zombie) {
				return 0;
			}
			attack(actor);
			return 2;	
		}
		else{
			return move;
		}
	}
	
	public int takeDamage(int damage){
		Tile tile = super.tile;
		super.takeDamage(damage);
		if(!isAlive()){
			explode(tile);
		}
		return super.currHealth;
	}
	
	private void explode(Tile tempTile){
		while(tempTile != null){
			tempTile = tempTile.getLeft();
			if(tempTile != null && tempTile.isOccupied()){
				Actor actor = tempTile.getOccupant();
				if (actor instanceof Plant) {
					actor.takeDamage(1000);
					return;
				}
			}
				
		}
	}
}