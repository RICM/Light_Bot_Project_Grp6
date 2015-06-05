package action;
import exception.MouvementEx;
import map.*;
import robot.Robot;

public class Activate implements int_Action{

	@Override
	public void execute(Robot r) throws MouvementEx {
	abstr_Case cprime = r.getCurrent_Case();
	if (isPossible(r,cprime)){
		if(cprime instanceof Teleporter_Case){
			r.setCurrent_Case(((Teleporter_Case)cprime).get_destination());
		}
		else{
			r.set_couleur(cprime.get_couleur());
		}
		throw (new MouvementEx("impossible d'allumer"));
		}
	}

	@Override
	public boolean isPossible(Robot r, abstr_Case c) {
		return (c instanceof Teleporter_Case || c instanceof Painted_Case);
	}
}
}
