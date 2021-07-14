blic class Persona {
	
    private String nome;
	private String url;

    
    public Persona(String nome, String url) {
    	setNome(nome);
		setUrl(url);

    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String toString() {
		return getNome();
	}

}
