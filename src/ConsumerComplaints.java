import java.util.*;
import java.io.*;
class Year{
    int nc;
    List<String> company_name;
    List<Integer> count;
    Year(){
        company_name=new ArrayList<>();
        count=new ArrayList<>();
    }
    public void cal(String cname){
        nc++;
        int index=company_name.indexOf(cname);
        if(index==-1){
            company_name.add(cname);
            count.add(1);
        }else{
            count.add(index,count.get(index)+1);
        }
    }
    public long calPer(){
        double m=Double.MIN_VALUE;
        for(int i:count){
            m=Math.max(i,m);
        }
        return Math.round((m/(double)nc)*100);
    }
}
class ConsumerComplaints{
    public static void main(String args[]) throws Exception{
        BufferedReader br=new BufferedReader(new FileReader(new File(args[0])));
        String line=br.readLine();
        Map<String,Map<String,Year>> m=new TreeMap<>();
        while((line=br.readLine())!=null){
            String a[]=new String[18];
            int k=0;
            boolean f=false;
            String s="";
            for(int i=0;i<line.length();i++){
                if(!f&&line.charAt(i)==','){
                    a[k++]=s;
                    s="";
                    continue;
                }
                if(line.charAt(i)=='"'){
                    f=!f;
                    if(!f){
                        s=s+line.charAt(i);
                        i++;
                        a[k++]=s;
                        s="";
                        continue;
                    }
                }
                s=s+line.charAt(i);
            }
            a[k++]=s;
            a[0]=a[0].substring(0,4);
            a[7]=a[7].toLowerCase();
            a[1]=a[1].toLowerCase();
            if(m.containsKey(a[1])){
                if(m.get(a[1]).containsKey(a[0])){
                    Year y=m.get(a[1]).get(a[0]);
                    y.cal(a[7]);
                }else{
                    Year y=new Year();
                    y.cal(a[7]);
                    m.get(a[1]).put(a[0],y);
                }
            }else{
                Map<String,Year> temp=new TreeMap<>();
                Year y=new Year();
                y.cal(a[7]);
                temp.put(a[0],y);
                m.put(a[1],temp);
            }
        }
        String s="";
        for(Map.Entry<String,Map<String,Year>> i:m.entrySet()){
            for(Map.Entry<String,Year> j:i.getValue().entrySet()){
                Year y=j.getValue();
                s=s+i.getKey()+","+j.getKey()+","+y.nc+","+y.company_name.size()+","+y.calPer()+"\n";
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
		writer.write(s);
		writer.close();
    }
}
