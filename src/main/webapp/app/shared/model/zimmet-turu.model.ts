export interface IZimmetTuru {
    id?: number;
    ad?: string;
    aciklama?: string;
}

export class ZimmetTuru implements IZimmetTuru {
    constructor(public id?: number, public ad?: string, public aciklama?: string) {}
}
