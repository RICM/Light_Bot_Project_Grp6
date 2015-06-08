package action;
import exception.MouvementEx;
import map.*;
import robot.Robot;

public class Activate implements int_Action{

	public static Activate activate(){
		return new Activate();
	}
	
	private Activate(){
		
	}
	@Override
	public void execute(Robot r) throws MouvementEx {
	abstr_Case cprime = r.getCurrent_Case();
	if (isPossible(r,cprime)){
		if(cprime.getClass().getCanonicalName().equals("map.Teleporter_Case")){
			r.setCurrent_Case(((Teleporter_Case)cprime).get_destination());
		}
		else {
			r.set_couleur(cprime.get_couleur());
		}
	}
	else{
		throw (new MouvementEx("impossible à utiliser"));
		}
	}

	@Override
	public boolean isPossible(Robot r, abstr_Case c) {
		return (c.getClass().getCanonicalName().equals("map.Teleporter_Case") || c.getClass().getCanonicalName().equals("map.Painted_Case"));
	}
}

