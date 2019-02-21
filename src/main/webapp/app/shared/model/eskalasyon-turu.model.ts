export interface IEskalasyonTuru {
    id?: number;
    ad?: string;
    aciklama?: string;
    kisaltma?: string;
}

export class EskalasyonTuru implements IEskalasyonTuru {
    constructor(public id?: number, public ad?: string, public aciklama?: string, public kisaltma?: string) {}
}
