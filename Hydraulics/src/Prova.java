import hydraulic.*;

public class Prova {

	public static void main(String[] args) {
		HSystem hs = new HSystem();
		
		Source s = new Source("sorgente");		hs.addElement(s);
		Tap t = new Tap("rubinetto");			hs.addElement(t);
		Split sp1 = new Split("elemento a T");	hs.addElement(sp1);
		Split sp2 = new Split("elemento a T");	hs.addElement(sp2);
		Sink sk1 = new Sink("pozzo 1");			hs.addElement(sk1);
		Sink sk2 = new Sink("pozzo 2");			hs.addElement(sk2);
		Sink sk3 = new Sink("pozzo 3");			hs.addElement(sk3);
		Sink sk4 = new Sink("pozzo 4");			hs.addElement(sk4);
		
		s.connect(t);
		t.connect(sp1);
		sp1.connect(sp2, 0);
		sp1.connect(sk1, 1);
		sp2.connect(sk2, 0);
		sp2.connect(sk3, 1);
		
		s.setFlow(100);
		t.setOpen(true);

		hs.simulate();
		System.out.println(hs.layout());
	}

}
