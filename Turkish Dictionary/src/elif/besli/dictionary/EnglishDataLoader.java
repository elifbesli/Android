package elif.besli.dictionary;

import java.util.List;

import android.os.AsyncTask;


public class EnglishDataLoader extends AsyncTask<String, Void, List<Bean>> {
	
	private DictionaryDB dictionaryDB;
	private WordListAdapter adapter;
	
	public EnglishDataLoader(DictionaryDB dictionaryDB, WordListAdapter adapter) {
		this.dictionaryDB = dictionaryDB;
		this.adapter = adapter;
	}

	@Override
	protected List<Bean> doInBackground(String... params) {
		
		return dictionaryDB.getEnglishWords(params[0]);
	}
	
	@Override
	protected void onPostExecute(List<Bean> result) {
		adapter.updateEntries(result);
	}
}
