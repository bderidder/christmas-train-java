package io.coderspotting.train.christmas.api.graphql.types;

public class ClientInfo
{
    private int id;
    private boolean master;
    private boolean admin;

    public ClientInfo(int id, boolean master, boolean admin)
    {
        this.id = id;
        this.master = master;
        this.admin = admin;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public boolean isMaster()
    {
        return master;
    }

    public void setMaster(boolean master)
    {
        this.master = master;
    }

    public boolean isAdmin()
    {
        return admin;
    }

    public void setAdmin(boolean admin)
    {
        this.admin = admin;
    }
}
