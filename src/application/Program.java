package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Funcionario;

/*Fazer um programa para ler os dados (nome, email e salário)
de funcionários a partir de um arquivo em formato .csv.
Em seguida mostrar, em ordem alfabética, o email dos
funcionários cujo salário seja superior a um dado valor
fornecido pelo usuário.
Mostrar também a soma dos salários dos funcionários cujo
nome começa com a letra 'M'.
Veja exemplo na próxima página.*/

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path: ");
		String path = sc.next();
		
		System.out.print("Enter salary: ");
		sc.nextLine();
		Double valor = sc.nextDouble();
		System.out.println("Email of people whose salary is more than " + String.format("%.2f", valor) + ": ");
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Funcionario> lista = new ArrayList<Funcionario>();
			
			String linha = br.readLine();
			while (linha != null) {
				String[] campo = linha.split(",");
				lista.add(new Funcionario(campo[0], campo[1], Double.parseDouble(campo[2])));				
				linha = br.readLine();
			}
			
			Comparator<String> fun = (f1, f2) -> f1
					.toUpperCase()
					.compareTo(f2.toUpperCase());
			
			List<String> listaEmails = lista.stream()
					.filter(e -> e.getSalario() > valor)
					.map(f -> f.getEmail()).sorted(fun)
					.collect(Collectors.toList());
			
			listaEmails.forEach(System.out::println);
			
			
			Double mediaSalario = lista.stream()
					.filter(f -> f.getNome().charAt(0) == 'M')
					.map(f -> f.getSalario())
					.reduce(0.0, (x, y) -> x + y);
			
			System.out.print("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", mediaSalario) );
			
			
		} catch (IOException e) {
			System.out.println("Erro:" + e.getMessage());
			e.getStackTrace();
		}
		
		sc.close();

	}

}
