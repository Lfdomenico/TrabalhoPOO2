package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AnotacaoDialog extends JDialog {
	private JTextArea txtAnotacao;
	private JTextField txtId;
        
        
        
	private JButton btnCriar, btnBuscar, btnDeletar, btnSalvar;

	private static int idCounter = 1;
	private static Map<Integer, String> anotacoes = new HashMap<>();

	public AnotacaoDialog(JFrame parent) {
		super(parent, "Anotações", true);
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());

		// Campos
		JPanel panelCampos = new JPanel(new BorderLayout(5, 5));
		txtId = new JTextField();
		txtId.setEditable(false);
		txtAnotacao = new JTextArea(10, 30);
		panelCampos.add(new JLabel("ID da Anotação:"), BorderLayout.NORTH);
		panelCampos.add(txtId, BorderLayout.CENTER);
		panelCampos.add(new JScrollPane(txtAnotacao), BorderLayout.SOUTH);

		// Botões
		JPanel panelBotoes = new JPanel(new FlowLayout());
		btnCriar = new JButton("Criar");
		btnBuscar = new JButton("Buscar");
		btnDeletar = new JButton("Deletar");
		btnSalvar = new JButton("Salvar");

		panelBotoes.add(btnCriar);
		panelBotoes.add(btnBuscar);
		panelBotoes.add(btnDeletar);
		panelBotoes.add(btnSalvar);

		add(panelCampos, BorderLayout.CENTER);
		add(panelBotoes, BorderLayout.SOUTH);

		// Ações
		btnCriar.addActionListener(e -> {
			txtId.setText(String.valueOf(idCounter++));
			txtAnotacao.setText("");
		});

		btnSalvar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText());
				anotacoes.put(id, txtAnotacao.getText());
				JOptionPane.showMessageDialog(this, "Anotação salva com sucesso!");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido.");
			}
		});

		btnBuscar.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(this, "Digite o ID da anotação:");
			try {
				int id = Integer.parseInt(input);
				if (anotacoes.containsKey(id)) {
					txtId.setText(String.valueOf(id));
					txtAnotacao.setText(anotacoes.get(id));
				} else {
					JOptionPane.showMessageDialog(this, "Anotação não encontrada.");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido.");
			}
		});

		btnDeletar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText());
				if (anotacoes.containsKey(id)) {
					anotacoes.remove(id);
					JOptionPane.showMessageDialog(this, "Anotação deletada.");
					txtId.setText("");
					txtAnotacao.setText("");
				} else {
					JOptionPane.showMessageDialog(this, "ID não encontrado.");
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "ID inválido.");
			}
		});
	}
}
