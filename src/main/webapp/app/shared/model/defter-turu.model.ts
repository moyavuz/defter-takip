export interface IDefterTuru {
    id?: number;
    ad?: string;
    aciklama?: string;
}

export class DefterTuru implements IDefterTuru {
    constructor(public id?: number, public ad?: string, public aciklama?: string) {}
}
