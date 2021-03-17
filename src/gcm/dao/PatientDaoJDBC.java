package gcm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gcm.model.Patient;

public class PatientDaoJDBC implements IPatientDAO {

	Statement stmt;
	
	public PatientDaoJDBC() {
		try {
			stmt = JdbcConnexion.connecter().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void add(Patient p) {
		try {
			String sql="insert into patient(nss,nom,prenom,adresse,datedenaissance,ville) "
					+ "values("+p.getNss()+",'"+p.getNom()+"','"+p.getPrenom()+"','"+p.getAdresse()+"','"+p.getDateDeNaissance()+"','"+p.getVille()+"')";
            stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(int nss) {
		try {
			String sql="delete from patient where nss="+nss;
            stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(int nss, String ville, String adresse) {
		try {
            String sql="update patient set ville='"+ville+"',adresse='"+adresse+"' where nss="+nss+" ";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public List<Patient> findAll() {
		List<Patient> list = new ArrayList<Patient>();
		try {
			ResultSet result = stmt.executeQuery("select * from patient");
			while (result.next()) {
				int rsnss = result.getInt("nss");
				String rsnom = result.getString("nom");
				String rsprenom = result.getString("prenom");
				String rsadresse = result.getString("adresse");
				String rsDatedeNaissance = result.getString("datedenaissance");
				String rsville = result.getString("ville");
				
				Patient p = new Patient();
				p.setNss(rsnss);
				p.setNom(rsnom);
				p.setPrenom(rsprenom);
				p.setAdresse(rsadresse);
				p.setDateDeNaissance(rsDatedeNaissance);
				p.setVille(rsville);
				
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean findUserByUsernameAndPassword(String username, String password) {
		
		try {
			ResultSet rs = stmt.executeQuery("select * from gcm_user where username='"+username+"' and password='"+password+"' ");
			if (rs.next())
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

}
