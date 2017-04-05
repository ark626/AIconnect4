package eshyperneat;

import neat.Neuron;

public class Layer {
public Neuron[] Neurons;

public  Layer(int x, int y){
	Neurons = new Neuron[x*y];
	for(int i = 0;i<x;i++){
		for(int j = 0;j<y;j++){
			Neurons[i*j] = new Neuron();
		}
	}
}

public Neuron get(int x, int y){
	int sqauare = (int)Math.floor(Math.sqrt(Neurons.length));
	return Neurons[sqauare*x+y];
}





}
