package worlds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.osbot.rs07.script.MethodProvider;

public class WorldHopping extends MethodProvider {
	
	ArrayList<Integer> f2pWorlds = new ArrayList<>(
		Arrays.asList(
			301, 308, 316, 326, 335, 371, 379, 380, 382, 383, 384, 393, 394, 397, 398, 399, 418, 425, 426, 430, 431, 433, 434, 435,
			436, 437, 438, 439, 440, 451, 452, 453, 454, 455, 456, 457, 458, 459, 469, 470, 471, 472, 473, 474, 475, 476, 483
		)
	);
	
	List<Integer> stf2p500 = Arrays.asList(381, 413, 419, 427, 468);
	List<Integer> stf2p750 = Arrays.asList(372, 385, 414, 432);
	
	ArrayList<Integer> p2pWorlds = new ArrayList<>(
		Arrays.asList(
			302, 303, 304, 305, 306, 307, 309, 310, 311, 312, 313, 314, 315, 317, 318, 319, 320, 321, 322, 323, 325, 327,
			328, 329, 330, 331, 332, 333, 334, 336, 337, 338, 339, 340, 341, 342, 344, 346, 347, 348, 350, 351, 352, 354,
			355, 356, 357, 358, 359, 360, 362, 365, 367, 368, 369, 370, 374, 375, 376, 377, 378, 386, 387, 388, 389, 390,
			395, 421, 422, 424, 443, 444, 445, 446, 463, 464, 465, 466, 477, 478, 479, 480, 481, 482, 484, 485
		)
	);
	
	List<Integer> stp2p1250 = Arrays.asList(353, 364, 429, 447);
	List<Integer> stp2p1500 = Arrays.asList(366, 416, 420, 448);
	List<Integer> stp2p1750 = Arrays.asList(373, 391, 449, 467);
	List<Integer> stp2p2000 = Arrays.asList(349, 361, 396, 428);
	List<Integer> stp2p2200 = Arrays.asList(363, 415, 450);
	
	ArrayList<Integer> worlds = new ArrayList<>();

	public WorldHopping(int skillTotal, String gameMode) {
		if(gameMode.equals("f2p")) {
			addF2pWorlds(skillTotal);
			worlds = f2pWorlds;
		}
		if(gameMode.equals("p2p")) {
			addP2pWorlds(skillTotal);
			worlds = p2pWorlds;
		}
		if(gameMode.equals("both")) {
			addF2pWorlds(skillTotal);
			addP2pWorlds(skillTotal);
			worlds.addAll(f2pWorlds);
			worlds.addAll(p2pWorlds);
		}
	}
	
	private void addF2pWorlds(int skillTotal) {
		if(skillTotal >= 500) {
			f2pWorlds.addAll(stf2p500);
		}
		if(skillTotal >= 750) {
			f2pWorlds.addAll(stf2p750);
		}
	}
	
	private void addP2pWorlds(int skillTotal) {
		if(skillTotal >= 1250) {
			p2pWorlds.addAll(stp2p1250);
		}
		if(skillTotal >= 1500) {
			p2pWorlds.addAll(stp2p1500);
		}
		if(skillTotal >= 1750) {
			p2pWorlds.addAll(stp2p1750);
		}
		if(skillTotal >= 2000) {
			p2pWorlds.addAll(stp2p2000);
		}
		if(skillTotal >= 2200) {
			p2pWorlds.addAll(stp2p2200);
		}
	}
		
	public int hopWorld() {
		return worlds.get(random(1, worlds.size() - 1));
	}
	
//	public static void main(String[] args) {
//		WorldHopping w = new WorldHopping(2200, "p2p");
//		System.out.println(w.worlds);
//		System.out.println(w.worlds.size());
//		w.hopWorld();
//	}

}
