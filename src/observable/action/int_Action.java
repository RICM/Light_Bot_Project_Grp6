package observable.action;

import observable.int_Observable;
import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public interface int_Action extends int_Observable {

	/**
	 *
	 * @param r robot qui execute l'action
	 * @throws MouvementEx
	 * @throws UnreachableCase
	 * @throws ActionEx
	 */
	public void execute (abstr_Robot r) throws MouvementEx, UnreachableCase, ActionEx;

	/**
	 *
	 * @param r robot qui essai d'executer l'action
	 * @param c case de destination dans les cas nécessaires
	 * @return true si la case et le robot respectent les conditions definie pour chaque action
	 */
	public boolean isPossible(abstr_Robot r,abstr_Case c);


}
